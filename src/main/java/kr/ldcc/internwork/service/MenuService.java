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

import javax.validation.Valid;
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
    public Response createMenu(@RequestBody @Valid MenuRequest menuRequest) {
        User user = userRepository.findById(menuRequest.getUser_id()).orElseThrow(() -> {
            log.error("createMenu Exception : [존재하지 않는 User ID]");
            return new InternWorkException.dataDuplicateException();
        });
        Menu parent = null;
        if (menuRequest.getParent_id() != null) {
            parent = menuRepository.findById(menuRequest.getParent_id()).orElseThrow(() -> {
                log.error("createMenu Exception : [존재하지 않는 Menu ID]");
                return new InternWorkException.dataDuplicateException();
            });
        }
        Menu menu = null;
        if (menuRequest.getOrder_id() != null) {
            int orderId = menuRequest.getOrder_id();
            menu = Menu.builder()
                    .orderId(orderId)
                    .parent(parent)
                    .state(menuRequest.getState())
                    .title(menuRequest.getTitle())
                    .registerUser(user)
                    .build();
            ArrayList<Menu> order = (ArrayList<Menu>) menuRepository.findAllByParentOrderByOrderId(parent);
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
            int orderId = menuRepository.findAllByParent(parent).size();
            menu = Menu.builder()
                    .orderId(orderId)
                    .parent(parent)
                    .state(menuRequest.getState())
                    .title(menuRequest.getTitle())
                    .registerUser(user)
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

    @Transactional
    public Response getDetailMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Menu ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        return MenuMapper.toGetDetailMenuResponse(menu);
    }

    @Transactional
    public Response updateMenu(Long menuId, @Valid MenuRequest menuRequest) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("updateMenu Exception : [존재하지 않는 Menu ID]");
            return new InternWorkException.dataDuplicateException();
        });
        Menu parent = menuRepository.findById(menuRequest.getParent_id()).orElseThrow(() -> {
            log.error("updateMenu Exception : [존재하지 않는 Parent ID]");
            return new InternWorkException.dataDuplicateException();
        });
        User user = userRepository.findById(menuRequest.getUser_id()).orElseThrow(() -> {
            log.error("updateMenu Exception : [존재하지 않는 User ID]");
            return new InternWorkException.dataDuplicateException();
        });
        Menu updateMenu = null;
        if (menuRequest.getOrder_id() != null) {
            int orderId = menuRequest.getOrder_id();
            updateMenu = Menu.builder()
                    .orderId(orderId)
                    .parent((parent != null) ? parent : menu.getParent())
                    .state((menuRequest.getState() != null) ? menuRequest.getState() : menu.getState())
                    .title((menuRequest.getTitle() != null) ? menuRequest.getTitle() : menu.getTitle())
                    .updateUser(user)
                    .build();
            ArrayList<Menu> oldOrder = (ArrayList<Menu>) menuRepository.findAllByParentOrderByOrderId(menu.getParent());
            ArrayList<Menu> newOrder = (ArrayList<Menu>) menuRepository.findAllByParentOrderByOrderId(parent);
            oldOrder.remove(menu.getOrderId());
            newOrder.add(orderId, updateMenu);
            oldOrder.forEach(menu1 -> {
                menu1.updateMenuOrderId(oldOrder.indexOf(menu1));
                try {
                    menuRepository.save(menu1);
                } catch (Exception e) {
                    log.error("updateMenu Exception : {}", e.getMessage());
                    throw new InternWorkException.dataDuplicateException();
                }
            });
            newOrder.forEach(menu1 -> {
                menu1.updateMenuOrderId(newOrder.indexOf(menu1));
                try {
                    menuRepository.save(menu1);
                } catch (Exception e) {
                    log.error("updateMenu Exception : {}", e.getMessage());
                    throw new InternWorkException.dataDuplicateException();
                }
            });
        } else {
            int orderId = menuRepository.findAllByParent(parent).size();
            updateMenu = Menu.builder()
                    .orderId(orderId)
                    .parent((parent != null) ? parent : menu.getParent())
                    .state((menuRequest.getState() != null) ? menuRequest.getState() : menu.getState())
                    .title((menuRequest.getTitle() != null) ? menuRequest.getTitle() : menu.getTitle())
                    .updateUser(user)
                    .build();
            ArrayList<Menu> oldOrder = (ArrayList<Menu>) menuRepository.findAllByParentOrderByOrderId(menu.getParent());
            oldOrder.remove(menu.getOrderId());
            oldOrder.forEach(menu1 -> {
                menu1.updateMenuOrderId(oldOrder.indexOf(menu1));
                try {
                    menuRepository.save(menu1);
                } catch (Exception e) {
                    log.error("updateMenu Exception : {}", e.getMessage());
                    throw new InternWorkException.dataDuplicateException();
                }
            });
            try {
                menuRepository.save(updateMenu);
            } catch (Exception e) {
                log.error("createMenu Exception : {}", e.getMessage());
                throw new InternWorkException.dataDuplicateException();
            }
        }
        return MenuMapper.toUpdateMenuResponse(updateMenu);
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
