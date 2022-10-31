package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.model.dto.MenuDto;
import kr.ldcc.internwork.model.dto.request.MenuRequest;
import kr.ldcc.internwork.model.entity.Menu;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.repository.MenuRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Transactional
    public MenuDto.CreateMenuResponse createMenu(@RequestBody MenuRequest.CreateMenuRequest createMenuRequest) {
        User registerUser = userRepository.findById(createMenuRequest.getUserId()).orElseThrow(() -> {
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
                    .registerUser(registerUser)
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
            return new MenuDto.CreateMenuResponse().setId(menu.getId());
        }
        Integer orderId = menuRepository.findAllByParent(parent).size();
        Menu menu = Menu.builder()
                .title(createMenuRequest.getTitle())
                .state(createMenuRequest.getState())
                .registerUser(registerUser)
                .parent(parent)
                .orderId(orderId)
                .build();
        try {
            menuRepository.save(menu);
        } catch (Exception e) {
            log.error("createMenu Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return new MenuDto.CreateMenuResponse().setId(menu.getId());
    }

    @Transactional
    public MenuDto.GetMenuListResponse getMenuList() {
        List<Menu> menus = menuRepository.findAll(Sort.by("orderId"));
        Map<Long, List<MenuDto.GetMenuListResponse>> groupingByParent = menus.stream().map(menu -> MenuDto.GetMenuListResponse.builder()
                .id(menu.getId())
                .orderId(menu.getOrderId())
                .parentId(menu.getParent() != null ? menu.getParent().getId() : 0)
                .title(menu.getTitle())
                .build()
        ).collect(Collectors.groupingBy(menuListResponse -> menuListResponse.getParentId()));
        MenuDto.GetMenuListResponse getMenuListResponse = MenuDto.GetMenuListResponse.builder().id(0L).build();
        addChildren(getMenuListResponse, groupingByParent);
        return getMenuListResponse;
    }

    public MenuDto.GetDetailMenuResponse getDetailMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Menu ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        return new MenuDto.GetDetailMenuResponse()
                .setId(menu.getId())
                .setMain(menu.getParent() != null ? (menu.getParent().getParent() != null ? menu.getParent().getParent().getTitle() : menu.getParent().getTitle()) : null)
                .setSmall(menu.getParent() != null ? (menu.getParent().getParent() != null ? menu.getParent().getTitle() : null) : null)
                .setTitle(menu.getTitle())
                .setState(menu.getState())
                .setRegisterUser(menu.getRegisterUser().getName())
                .setUpdateUser((menu.getUpdateUser() != null) ? menu.getUpdateUser().getName() : null)
                .setRegisterDate(menu.getRegisterDate())
                .setUpdateDate(menu.getUpdateDate());
    }

    @Transactional
    public MenuDto.UpdateMenuResponse updateMenu(Long menuId, MenuRequest.UpdateMenuRequest updateMenuRequest) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Menu ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        User updateUser = userRepository.findById(updateMenuRequest.getUserId()).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        menu.updateMenuTitle(updateMenuRequest.getTitle() != null ? updateMenuRequest.getTitle() : menu.getTitle());
        menu.updateMenuState(updateMenuRequest.getState() != null ? updateMenuRequest.getState() : menu.getState());
        menu.updateMenuUpdateUser(updateUser);
        if (updateMenuRequest.getParentId() == 0L || updateMenuRequest.getParentId().equals(menu.getParent().getId())) {
            if (updateMenuRequest.getOrderId() == null || updateMenuRequest.getOrderId().equals(menu.getOrderId())) {
                try {
                    menuRepository.save(menu);
                } catch (Exception e) {
                    log.error("updateMenu Exception : {}", e.getMessage());
                    throw new InternWorkException.dataDuplicateException();
                }
                return new MenuDto.UpdateMenuResponse()
                        .setId(menu.getId())
                        .setOrderId(menu.getOrderId())
                        .setParent(menu.getParent().getId())
                        .setState(menu.getState())
                        .setTitle(menu.getTitle())
                        .setRegisterUser(menu.getRegisterUser())
                        .setUpdateUser(menu.getUpdateUser());
            }
            ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), menu.getParent());
            order.remove(menu);
            Integer orderId = updateMenuRequest.getOrderId();
            menu.updateMenuOrderId(orderId);
            order.add(orderId, menu);
            order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
            try {
                menuRepository.saveAll(order);
            } catch (Exception e) {
                log.error("updateMenu Exception : {}", e.getMessage());
                throw new InternWorkException.dataDuplicateException();
            }
            return new MenuDto.UpdateMenuResponse()
                    .setId(menu.getId())
                    .setOrderId(menu.getOrderId())
                    .setParent(menu.getParent().getId())
                    .setState(menu.getState())
                    .setTitle(menu.getTitle())
                    .setRegisterUser(menu.getRegisterUser())
                    .setUpdateUser(menu.getUpdateUser());
        }
        if (updateMenuRequest.getOrderId() == null) {
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
            return new MenuDto.UpdateMenuResponse()
                    .setId(menu.getId())
                    .setOrderId(menu.getOrderId())
                    .setParent(menu.getParent().getId())
                    .setState(menu.getState())
                    .setTitle(menu.getTitle())
                    .setRegisterUser(menu.getRegisterUser())
                    .setUpdateUser(menu.getUpdateUser());
        }
        Menu parent = menuRepository.findById(updateMenuRequest.getParentId()).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Parent ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        Integer orderId = updateMenuRequest.getOrderId();
        menu.updateMenuParent(parent);
        menu.updateMenuOrderId(orderId);
        ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), parent);
        order.add(orderId, menu);
        order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
        try {
            menuRepository.saveAll(order);
        } catch (Exception e) {
            log.error("updateMenu Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return new MenuDto.UpdateMenuResponse()
                .setId(menu.getId())
                .setOrderId(menu.getOrderId())
                .setParent(menu.getParent().getId())
                .setState(menu.getState())
                .setTitle(menu.getTitle())
                .setRegisterUser(menu.getRegisterUser())
                .setUpdateUser(menu.getUpdateUser());
    }

    @Transactional
    public MenuDto.DeleteMenuResponse deleteMenu(Long menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent() && menuRepository.findAllByParent(menu.get()).size() == 0) {
            menuRepository.deleteById(menuId);
            return new MenuDto.DeleteMenuResponse().setId(menu.get().getId());
        }
        throw new InternWorkException.dataNotFoundException();
    }

    private static void addChildren(MenuDto.GetMenuListResponse parent, Map<Long, List<MenuDto.GetMenuListResponse>> groupingByParentId) {
        List<MenuDto.GetMenuListResponse> children = groupingByParentId.get(parent.getId());
        if (children == null) {
            return;
        }
        parent.setChildren(children);
        children.forEach(menuListResponse -> addChildren(menuListResponse, groupingByParentId));
    }
}
