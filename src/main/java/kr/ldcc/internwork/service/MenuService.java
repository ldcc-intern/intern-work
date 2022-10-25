package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
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
    public Long createMenu(@RequestBody MenuRequest.CreateMenuRequest createMenuRequest) {
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
        if (createMenuRequest.getOrderId() != null) {
            Integer orderId = createMenuRequest.getOrderId();
            Menu menu = Menu.builder()
                    .title(createMenuRequest.getTitle())
                    .state(createMenuRequest.getState())
                    .registerUser(user)
                    .parent(parent)
                    .orderId(orderId)
                    .build();
            ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), parent);
            order.add(orderId, menu);
            order.forEach(menu1 -> {
                menu1.updateMenuOrderId(order.indexOf(menu1));
            });
            try {
                menuRepository.saveAll(order);
            } catch (Exception e) {
                log.error("createMenu Exception : {}", e.getMessage());
                throw new InternWorkException.dataDuplicateException();
            }
            return menu.getId();
        }
        Integer orderId = menuRepository.findAllByParent(parent).size();
        Menu menu = Menu.builder()
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
        return menu.getId();
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
        User user = userRepository.findById(updateMenuRequest.getUserId()).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        menu.updateMenuTitle(updateMenuRequest.getTitle() != null ? updateMenuRequest.getTitle() : menu.getTitle());
        menu.updateMenuState(updateMenuRequest.getState() != null ? updateMenuRequest.getState() : menu.getState());
        menu.updateMenuUpdateUser(user);
        if (updateMenuRequest.getParentId() != 0) {
            if (updateMenuRequest.getOrderId() != null || updateMenuRequest.getOrderId() == menu.getOrderId()) {
                try {
                    menuRepository.save(menu);
                } catch (Exception e) {
                    log.error("updateMenu Exception : {}", e.getMessage());
                    throw new InternWorkException.dataDuplicateException();
                }
                return MenuMapper.toUpdateMenuResponse(menu);
            }
            ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), menu.getParent());
            order.remove(menu);
            Integer orderId = updateMenuRequest.getOrderId();
            order.add(orderId, menu);
            order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
            try {
                menuRepository.saveAll(order);
            } catch (Exception e) {
                log.error("updateMenu Exception : {}", e.getMessage());
                throw new InternWorkException.dataDuplicateException();
            }
            return MenuMapper.toUpdateMenuResponse(menu);
        }
        if (updateMenuRequest.getParentId() != menu.getParent().getId()) {
            if (updateMenuRequest.getOrderId() != null) {
                Menu parent = menuRepository.findById(updateMenuRequest.getParentId()).orElseThrow(() -> {
                    log.error("getDetailMenu Exception : [존재하지 않는 Parent ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
                    return new InternWorkException.dataNotFoundException();
                });
                menu.updateMenuParent(parent);
                Integer orderId = updateMenuRequest.getOrderId() != null ? updateMenuRequest.getOrderId() : menu.getOrderId();
                ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), parent);
                order.add(orderId, menu);
                order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
                try {
                    menuRepository.saveAll(order);
                } catch (Exception e) {
                    log.error("updateMenu Exception : {}", e.getMessage());
                    throw new InternWorkException.dataDuplicateException();
                }
                return MenuMapper.toUpdateMenuResponse(menu);
            }
            Menu parent = menuRepository.findById(updateMenuRequest.getParentId()).orElseThrow(() -> {
                log.error("getDetailMenu Exception : [존재하지 않는 Parent ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
                return new InternWorkException.dataNotFoundException();
            });
            Integer orderId = menuRepository.findAllByParent(parent).size();
            menu.updateMenuParent(parent);
            menu.updateMenuOrderId(orderId);
            try {
                menuRepository.save(menu);
            } catch (Exception e) {
                log.error("updateMenu Exception : {}", e.getMessage());
                throw new InternWorkException.dataDuplicateException();
            }
            return MenuMapper.toUpdateMenuResponse(menu);
        }
        if (updateMenuRequest.getOrderId() == null || updateMenuRequest.getOrderId() == menu.getOrderId()) {
            try {
                menuRepository.save(menu);
            } catch (Exception e) {
                log.error("updateMenu Exception : {}", e.getMessage());
                throw new InternWorkException.dataDuplicateException();
            }
            return MenuMapper.toUpdateMenuResponse(menu);
        }
        ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), menu.getParent());
        order.remove(menu);
        Integer orderId = updateMenuRequest.getOrderId();
        order.add(orderId, menu);
        order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
        try {
            menuRepository.saveAll(order);
        } catch (Exception e) {
            log.error("updateMenu Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return MenuMapper.toUpdateMenuResponse(menu);
    }

    @Transactional
    public Response deleteMenu(Long menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent()) {
            if (menuRepository.findAllByParent(menu.get()) == null)
                menuRepository.deleteById(menuId);
            return MenuMapper.toDeleteMenuResponse();
        }
        throw new InternWorkException.dataNotFoundException();
    }
}
