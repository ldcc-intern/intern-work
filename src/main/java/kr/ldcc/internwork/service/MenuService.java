package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Transactional
    public Menu createMenu(@RequestBody MenuRequest.CreateMenuRequest createMenuRequest) {
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
            return menu;
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
        return menu;
    }

    @Transactional
    public List<Menu> getMenuList() {
        return menuRepository.findAll(Sort.by("orderId"));
    }

    public Menu getDetailMenu(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Menu ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
    }

    @Transactional
    public Menu updateMenu(Long menuId, MenuRequest.UpdateMenuRequest updateMenuRequest) {
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
        if (updateMenuRequest.getParentId() == 0) {
            log.info("분기 1");
            if (updateMenuRequest.getOrderId() == null || updateMenuRequest.getOrderId() == menu.getOrderId()) {
                log.info("분기 2");
                menuRepository.save(menu);
                log.info("테스트 1, 위치 변경x, save 만");
                return menu;
            }
            log.info("분기 3");
            ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), menu.getParent());
            order.remove(menu);
            Integer orderId = updateMenuRequest.getOrderId();
            menu.updateMenuOrderId(orderId);
            order.add(orderId, menu);
            order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
            menuRepository.saveAll(order);
            log.info("테스트 2, 위치 변경o -> 기존 배열에서 삭제 후 -> OrderId에 추가, save All");
            return menu;
        }
        if (updateMenuRequest.getParentId() == menu.getParent().getId()) {
            log.info("분기 4");
            if (updateMenuRequest.getOrderId() == null || updateMenuRequest.getOrderId() == menu.getOrderId()) {
                log.info("분기 5");
                menuRepository.save(menu);
                log.info("테스트 3, 위치 변경x, save 만");
                return menu;
            }
            log.info("분기 6");
            ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), menu.getParent());
            order.remove(menu);
            Integer orderId = updateMenuRequest.getOrderId();
            order.add(orderId, menu);
            order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
            menuRepository.saveAll(order);
            log.info("테스트 4, 위치 변경o -> 기존 배열에서 삭제 후 -> OrderId에 추가, save All");
            return menu;
        }
        if (updateMenuRequest.getOrderId() == null) {
            log.info("분기 7");
            Menu parent = menuRepository.findById(updateMenuRequest.getParentId()).orElseThrow(() -> {
                log.error("getDetailMenu Exception : [존재하지 않는 Parent ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
                return new InternWorkException.dataNotFoundException();
            });
            Integer orderId = menuRepository.findAllByParent(parent).size();
            menu.updateMenuParent(parent);
            menu.updateMenuOrderId(orderId);
            menuRepository.save(menu);
            log.info("테스트 5, Parent 변경, 맨 뒤에 추가, save");
            return menu;
        }
        log.info("분기 8");
        Menu parent = menuRepository.findById(updateMenuRequest.getParentId()).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Parent ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        menu.updateMenuParent(parent);
        Integer orderId = updateMenuRequest.getOrderId() != null ? updateMenuRequest.getOrderId() : menu.getOrderId();
        ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), parent);
        order.add(orderId, menu);
        order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
        menuRepository.saveAll(order);
        log.info("테스트 6, Parent 변경, OrderId에 추가, save All");
        return menu;
    }

    @Transactional
    public Menu deleteMenu(Long menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent() && menuRepository.findAllByParent(menu.get()).size() == 0) {
            menuRepository.deleteById(menuId);
            return menu.get();
        }
        throw new InternWorkException.dataNotFoundException();
    }
}
