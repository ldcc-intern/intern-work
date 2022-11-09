package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.model.dto.MenuDto;
import kr.ldcc.internwork.model.dto.request.MenuRequest;
import kr.ldcc.internwork.model.entity.Menu;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.MenuMapper;
import kr.ldcc.internwork.repository.MenuRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MenuService {
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createMenu(@RequestBody MenuRequest request) {
        User registerUser = userRepository.findById(request.getUserId()).orElseThrow(() -> {
            log.error("createMenu Exception | [존재하지 않는 User ID : " + request.getUserId() + "]");
            return new InternWorkException.dataNotFoundException();
        });
        Menu parent = null;
        if (request.getParentId() != null) {
            parent = menuRepository.findById(request.getParentId()).orElseThrow(() -> {
                log.error("createMenu Exception | [존재하지 않는 Parent ID : " + request.getParentId() + "]");
                return new InternWorkException.dataNotFoundException();
            });
        }
        if (menuRepository.findByTitle(request.getTitle()).isPresent()) {
            log.error("createMenu Exception | [존재하는 Menu Title : " + request.getTitle() + "]");
            throw new InternWorkException.dataDuplicateException();
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
            order.add(orderId, menu);
            order.forEach(menu1 -> {
                menu1.updateMenuOrderId(order.indexOf(menu1));
            });
            try {
                menuRepository.saveAll(order);
            } catch (Exception e) {
                log.error("createMenu Exception | " + e.getMessage());
                throw new InternWorkException.dataDuplicateException();
            }
            return menu.getId();
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
            log.error("createMenu Exception | " + e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return menu.getId();
    }

    @Transactional
    public MenuDto.GetMenuListResponse getMenuList() {
        return MenuMapper.toGetMenuListResponse(menuRepository.findAll(Sort.by("orderId")));
    }

    @Transactional
    public MenuDto.GetDetailMenuResponse getDetailMenu(Long menuId) {
        return MenuMapper.toGetDetailMenuResponse(menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("getDetailMenu Exception | [존재하지 않는 Menu ID : " + menuId + "]");
            return new InternWorkException.dataNotFoundException();
        }));
    }

    @Transactional
    public MenuDto.UpdateMenuResponse updateMenu(Long menuId, MenuRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> {
            log.error("updateMenu Exception | [존재하지 않는 User ID : " + request.getUserId() + "]");
            return new InternWorkException.dataNotFoundException();
        });
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            log.error("updateMenu Exception | [존재하지 않는 Menu ID : " + menuId + "]");
            return new InternWorkException.dataNotFoundException();
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
                log.error("updateMenu Exception | " + e.getMessage());
                throw new InternWorkException.dataDuplicateException();
            }
            return MenuMapper.toUpdateMenuResponse(menu);
        }
        ArrayList<Menu> order = menuRepository.findAllByParent(Sort.by("orderId"), parent);
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
            log.error("updateMenu Exception | " + e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return MenuMapper.toUpdateMenuResponse(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent() && menuRepository.findAllByParent(menu.get()).size() == 0) {
            menuRepository.deleteById(menuId);
            return;
        }
        log.error("deleteUser Exception | [존재하지 않는 Menu ID : " + menuId + "] | ");
        throw new InternWorkException.dataDeleteException();
    }

    @Transactional
    private Menu findParent(Menu menu, Long parentId) {
        if (parentId == null) {
            return menu.getParent();
        }
        if (parentId == 0) {
            return null;
        }
        return menuRepository.findById(parentId).orElseThrow(() -> {
            log.error("findParent Exception | [존재하지 않는 Menu ID : " + parentId + "] | ");
            return new InternWorkException.dataNotFoundException();
        });
    }
}
