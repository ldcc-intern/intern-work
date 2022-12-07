package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException.DataDeleteException;
import kr.ldcc.internwork.common.exception.InternWorkException.DataDuplicateException;
import kr.ldcc.internwork.common.exception.InternWorkException.DataNotFoundException;
import kr.ldcc.internwork.common.exception.InternWorkException.DataOutOfBoundsException;
import kr.ldcc.internwork.model.dto.MenuDto;
import kr.ldcc.internwork.model.dto.MenuDto.GetDetailMenuResponse;
import kr.ldcc.internwork.model.dto.MenuDto.GetMenuListResponse;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    public void createMenu(@RequestBody MenuRequest request) {
        User registerUser = userRepository.findById(request.getUserId()).orElseThrow(() -> {
            log.error("createMenu Exception | [존재하지 않는 User ID : " + request.getUserId() + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        Menu parent = null;
        if (request.getParentId() != null) {
            parent = menuRepository.findById(request.getParentId()).orElseThrow(() -> {
                log.error("createMenu Exception | [존재하지 않는 Parent ID : " + request.getParentId() + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
                return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            });
        }
        if (menuRepository.findByTitle(request.getTitle()).isPresent()) {
            log.error("createMenu Exception | [존재하는 Menu Title : " + request.getTitle() + "]", ExceptionCode.DATA_DUPLICATE_EXCEPTION);
            throw new DataDuplicateException(ExceptionCode.DATA_DUPLICATE_EXCEPTION);
        }
        if (request.getOrderId() != null) {
            Integer orderId = request.getOrderId();
            Menu menu = Menu.builder()
                    .title(request.getTitle())
                    .state(request.getState())
                    .registerUser(registerUser)
                    .parent(parent)
                    .orderId(orderId)
                    .build();
            ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), parent);
            try {
                order.add(orderId, menu);
            } catch (Exception e) {
                log.error("createMenu Exception | [Max OrderId : " + menuRepository.findAllByParent(parent).size() + "]", ExceptionCode.DATA_OUT_OF_BOUNDS_EXCEPTION);
                throw new DataOutOfBoundsException(ExceptionCode.DATA_OUT_OF_BOUNDS_EXCEPTION);
            }
            order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
            try {
                menuRepository.saveAll(order);
            } catch (Exception e) {
                log.error("createMenu Exception", ExceptionCode.DATA_DUPLICATE_EXCEPTION);
                throw new DataDuplicateException(ExceptionCode.DATA_DUPLICATE_EXCEPTION);
            }
            return;
        }
        Integer orderId = menuRepository.findAllByParent(parent).size();
        Menu menu = Menu.builder()
                .title(request.getTitle())
                .state(request.getState())
                .registerUser(registerUser)
                .parent(parent)
                .orderId(orderId)
                .build();
        try {
            menuRepository.save(menu);
        } catch (Exception e) {
            log.error("createMenu Exception", ExceptionCode.DATA_DUPLICATE_EXCEPTION);
            throw new DataDuplicateException(ExceptionCode.DATA_DUPLICATE_EXCEPTION);
        }
    }

    public GetMenuListResponse getMenuList() {
        return MenuDto.buildGetMenuList(menuRepository.findAll(Sort.by("orderId")));
    }

    public GetDetailMenuResponse getDetailMenu(Long menuId) {
        return MenuDto.buildDetailMenu(menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("getDetailMenu Exception | [존재하지 않는 Menu ID : " + menuId + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        }));
    }

    public void updateMenu(Long menuId, MenuRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> {
            log.error("updateMenu Exception | [존재하지 않는 User ID : " + request.getUserId() + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("updateMenu Exception | [존재하지 않는 Menu ID " + menuId + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        Menu parent = findParent(menu, request.getParentId());
        menu.updateMenuTitle(request.getTitle() != null ? request.getTitle() : menu.getTitle());
        menu.updateMenuState(request.getState());
        menu.updateMenuUpdateUser(user);
        if (request.getOrderId() == null) {
            if (parent != menu.getParent()) {
                menu.updateMenuOrderId(menuRepository.findAllByParent(parent).size());
            }
            menu.updateMenuParent(parent);
            try {
                menuRepository.save(menu);
            } catch (Exception e) {
                log.error("updateMenu Exception", ExceptionCode.DATA_DUPLICATE_EXCEPTION, e.getMessage());
                throw new DataDuplicateException(ExceptionCode.DATA_DUPLICATE_EXCEPTION);
            }
            return;
        }
        ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), parent);
        if (request.getOrderId() > order.size()) {
            log.error("updateMenu Exception | [Max OrderId : " + order.size() + "]", ExceptionCode.DATA_OUT_OF_BOUNDS_EXCEPTION);
            throw new DataOutOfBoundsException(ExceptionCode.DATA_OUT_OF_BOUNDS_EXCEPTION);
        }
        if (parent == menu.getParent()) {
            order.remove(menu);
        }
        menu.updateMenuParent(parent);
        menu.updateMenuOrderId(request.getOrderId());
        order.add(menu);
        order.forEach(menu1 -> menu1.updateMenuOrderId(order.indexOf(menu1)));
        try {
            menuRepository.saveAll(order);
        } catch (Exception e) {
            log.error("updateMenu Exception", ExceptionCode.DATA_DUPLICATE_EXCEPTION, e.getMessage());
            throw new DataDuplicateException(ExceptionCode.DATA_DUPLICATE_EXCEPTION);
        }
    }

    public void deleteMenu(Long menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent() && menuRepository.findAllByParent(menu.get()).size() == 0) {
            menuRepository.deleteById(menuId);
            return;
        }
        log.error("deleteUser Exception | [존재하지 않는 Menu ID : " + menuId + "]", ExceptionCode.DATA_DELETE_EXCEPTION);
        throw new DataDeleteException(ExceptionCode.DATA_DELETE_EXCEPTION);
    }

    private Menu findParent(Menu menu, Long parentId) {
        if (parentId == null) {
            return menu.getParent();
        }
        if (parentId == 0) {
            return null;
        }
        return menuRepository.findById(parentId).orElseThrow(() -> {
            log.error("findParent Exception | [존재하지 않는 Menu ID : " + parentId + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
    }
}
