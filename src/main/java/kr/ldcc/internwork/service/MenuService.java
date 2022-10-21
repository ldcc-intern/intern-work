package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.model.dto.request.MenuRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Menu;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.MenuMapper;
import kr.ldcc.internwork.repository.MenuRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MenuService {
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, UserRepository userRepository) {
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Response createMenu(@RequestBody MenuRequest.CreateMenuRequest createMenuRequest) {
        User user = userRepository.findById(createMenuRequest.getUserId()).orElseThrow(() -> {
            log.error("createMenu Exception : [존재하지 않는 User ID]");
            return new InternWorkException.dataDuplicateException();
        });
        Menu parent = null;
        if (createMenuRequest.getParentId() != null) {
            parent = menuRepository.findById(createMenuRequest.getParentId()).orElseThrow(() -> {
                log.error("createMenu Exception : [존재하지 않는 Parent ID]");
                return new InternWorkException.dataDuplicateException();
            });
        }
        Menu menu = null;
        if (createMenuRequest.getOrderId() != null) {
            Integer orderId = createMenuRequest.getOrderId();
            menu = Menu.builder()
                    .title(createMenuRequest.getTitle())
                    .state(createMenuRequest.getState())
                    .registerUser(user)
                    .parent(parent)
                    .orderId(orderId)
                    .build();
            ArrayList<Menu> order = menuRepository.findAllByParentOrderByOrderId(parent);
            order.add(orderId, menu);
            order.forEach(menu1 -> {
                menu1.updateMenuOrderId(order.indexOf(menu1));
                try {
                    menuRepository.save(menu1);
                } catch (Exception e) {
                    log.error("createMenu Exception : {}", e.getMessage());
                    throw new InternWorkException.dataDuplicateException();
                }
            });
        } else {
            Integer orderId = menuRepository.findAllByParent(parent).size();
            menu = Menu.builder()
                    .title(createMenuRequest.getTitle())
                    .state(createMenuRequest.getState())
                    .registerUser(user)
                    .parent(parent)
                    .orderId(orderId)
                    .build();
            try {
                menuRepository.save(menu);
            } catch (Exception e) {
                log.error("createMenu Exception : {}", e.getMessage());
                throw new InternWorkException.dataDuplicateException();
            }
        }
        return MenuMapper.toCreateMenuResponse(menu.getId());
    }

    @Transactional
    public Response getMenuList() {
        List<Menu> menus = menuRepository.findAll(Sort.by("orderId"));
        return MenuMapper.toGetMenuListResponse(menus);
    }

    public Response getDetailMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Menu ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        return MenuMapper.toGetDetailMenuResponse(menu);
    }

    @Transactional
    public Response updateMenu(Long menuId, MenuRequest.updateMenuRequest updateMenuRequest) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Menu ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        String title = updateMenuRequest.getTitle() != null ? updateMenuRequest.getTitle() : menu.getTitle();
        MenuType state = updateMenuRequest.getState() != null ? updateMenuRequest.getState() : menu.getState();
        User user = userRepository.findById(updateMenuRequest.getUserId()).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        menu.updateMenuTitle(title);
        menu.updateMenuState(state);
        menu.updateMenuUpdateUser(user);
        if (updateMenuRequest.getParentId() != 0L) {
            if (updateMenuRequest.getParentId() == menu.getParent().getId()) {
                if (updateMenuRequest.getOrderId() != menu.getOrderId()) {
                    ArrayList<Menu> menus = menuRepository.findAllByParentOrderByOrderId(menu.getParent());
                    menus.remove(menus.indexOf(menu));
                    menus.add(updateMenuRequest.getOrderId(), menu);
                    menus.forEach(menu1 -> {
                        menu1.updateMenuOrderId(menus.indexOf(menu1));
                        try {
                            menuRepository.save(menu1);
                        } catch (Exception e) {
                            log.error("updateMenu Exception : {}", e.getMessage());
                            throw new InternWorkException.dataDuplicateException();
                        }
                    });
                    return MenuMapper.toUpdateMenuResponse(menu);
//                    return Response.ok().setData("Parent 기존과 같을 때, Order 기존과 다를 때 (부모 유지, 위치 이동)");
                }
            } else {
                if (updateMenuRequest.getOrderId() != null) {
                    ArrayList<Menu> oldMenus = menuRepository.findAllByParentOrderByOrderId(menu.getParent());
                    oldMenus.remove(oldMenus.indexOf(menu));
                    oldMenus.forEach(menu1 -> {
                        menu1.updateMenuOrderId(oldMenus.indexOf(menu1));
                        try {
                            menuRepository.save(menu1);
                        } catch (Exception e) {
                            log.error("updateMenu Exception : {}", e.getMessage());
                            throw new InternWorkException.dataDuplicateException();
                        }
                    });
                    Menu parent = menuRepository.findById(updateMenuRequest.getParentId()).orElseThrow(() -> {
                        log.error("updateMenu Exception : [존재하지 않는 Parent ID]");
                        return new InternWorkException.dataDuplicateException();
                    });
                    ArrayList<Menu> newMenus = menuRepository.findAllByParentOrderByOrderId(parent);
                    Integer orderId = updateMenuRequest.getOrderId();
                    newMenus.add(orderId, menu);
                    menu.updateMenuParent(parent);
                    menu.updateMenuOrderId(orderId);
                    newMenus.forEach(menu1 -> {
                        menu1.updateMenuOrderId(newMenus.indexOf(menu1));
                        try {
                            menuRepository.save(menu1);
                        } catch (Exception e) {
                            log.error("updateMenu Exception : {}", e.getMessage());
                            throw new InternWorkException.dataDuplicateException();
                        }
                    });
                    return MenuMapper.toUpdateMenuResponse(menu);
//                    return Response.ok().setData("Parent 기존과 다를 때, Order 기존과 같거나 다를 때 (부모 변경, 위치 이동)");
                } else {
//                    oldMenus.remove(oldMenus.indexOf(menu));
//                    oldMenus.forEach(menu1 -> {
//                        menu1.updateMenuOrderId(oldMenus.indexOf(menu1));
//                        try {
//                            menuRepository.save(menu1);
//                        } catch (Exception e) {
//                            log.error("updateMenu Exception : {}", e.getMessage());
//                            throw new InternWorkException.dataDuplicateException();
//                        }
//                    });
//                    Menu parent = menuRepository.findById(updateMenuRequest.getParentId()).orElseThrow(() -> {
//                        log.error("updateMenu Exception : [존재하지 않는 Parent ID]");
//                        return new InternWorkException.dataDuplicateException();
//                    });
//                    Integer orderId = menuRepository.findAllByParent(parent).size();
                    return Response.ok().setData("Parent 기존과 다를 때, Order 존재하지 않을 때 (부모 변경, 위치 뒤에 추가)");
                }
            }
        } else {
            if (updateMenuRequest.getOrderId() != null) {
                if (updateMenuRequest.getOrderId() != menu.getOrderId()) {
                    return Response.ok().setData("Parent 존재하지 않을 때, Order 기존과 다를 때 (부모 유지, 위치 이동)");
                }
            }
        }
        try {
            menuRepository.save(menu);
        } catch (Exception e) {
            log.error("updateMenu Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return MenuMapper.toUpdateMenuResponse(menu);
        //        return Response.ok().setData("Parent 존재하지 않을 때, Order 존재하지 않을 때 (부모 유지, 위치 유지)");
    }

    @Transactional
    public Response deleteMenu(Long menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent()) {
            menuRepository.deleteById(menuId);
            return MenuMapper.toDeleteMenuResponse();
        }
        throw new InternWorkException.dataNotFoundException();
    }
}
