package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.model.dto.request.MenuRequest;
import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.entity.Menu;
import kr.ldcc.internwork.repository.MenuRepository;
import kr.ldcc.internwork.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MenuServiceTest {
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserRepository userRepository;

    @Test/*parent == null, orderId == null*/
    void createMenu1() {
//        given
        UserRequest.CreateUserRequest createUserRequest = new UserRequest.CreateUserRequest().setName("이름");
        Long userId = userService.createUser(createUserRequest);
        MenuRequest.CreateMenuRequest createMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test Menu")
                .setState(MenuType.ON)
                .setUserId(userId);
//        when
        Long menuId = menuService.createMenu(createMenuRequest);
//        then
        Menu menu = menuRepository.findById(menuId).get();
        Integer size = menuRepository.findAllByParent(null).size() - 1;
        assertEquals(createMenuRequest.getTitle(), menu.getTitle());
        assertEquals(createMenuRequest.getState(), menu.getState());
        assertEquals(createMenuRequest.getUserId(), menu.getRegisterUser().getId());
        assertEquals(menu.getParent(), null);
        assertEquals(menu.getOrderId(), size);
    }

    @Test/*parent == null*/
    void createMenu2() {
//        given
        UserRequest.CreateUserRequest userRequest = new UserRequest.CreateUserRequest().setName("이름");
        Long userId = userService.createUser(userRequest);
        MenuRequest.CreateMenuRequest menuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test Menu")
                .setState(MenuType.ON)
                .setUserId(userId);
        Long orderId = menuService.createMenu(menuRequest);
        Menu order = menuRepository.findById(orderId).get();
        MenuRequest.CreateMenuRequest createMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test Menu")
                .setState(MenuType.ON)
                .setUserId(userId)
                .setOrderId(order.getOrderId());
//        when
        Long menuId = menuService.createMenu(createMenuRequest);
//        then
        Menu menu = menuRepository.findById(menuId).get();
        assertEquals(createMenuRequest.getTitle(), menu.getTitle());
        assertEquals(createMenuRequest.getState(), menu.getState());
        assertEquals(createMenuRequest.getUserId(), menu.getRegisterUser().getId());
        assertEquals(createMenuRequest.getOrderId(), menu.getOrderId());
        assertEquals(menu.getOrderId(), order.getOrderId());
        assertEquals(menu.getParent(), null);
    }

    @Test
    void createMenu3() {
    }

    @Test
    void createMenu4() {
    }

    @Test
    void getDetailMenu() {
    }

    @Test
    void updateMenu() {
    }

    @Test
    void deleteMenu() {
    }
}