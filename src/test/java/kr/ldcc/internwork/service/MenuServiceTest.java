package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.model.dto.request.MenuRequest;
import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.entity.Menu;
import kr.ldcc.internwork.model.entity.User;
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
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        MenuRequest.CreateMenuRequest createMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId());
//        when
        Menu menu = menuService.createMenu(createMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        Integer size = menuRepository.findAllByParent(null).size() - 1;
        assertEquals(findMenu.getTitle(), createMenuRequest.getTitle());
        assertEquals(findMenu.getState(), createMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenuRequest.getUserId());
        assertEquals(findMenu.getParent(), null);
        assertEquals(findMenu.getOrderId(), size);
    }

    @Test/*parent == null, orderId != null*/
    void createMenu2() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        Menu order = menuService.createMenu(
                new MenuRequest.CreateMenuRequest()
                        .setTitle("Test 메뉴")
                        .setState(MenuType.ON)
                        .setUserId(registerUser.getId()));
        MenuRequest.CreateMenuRequest createMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setOrderId(order.getOrderId());
//        when
        Menu menu = menuService.createMenu(createMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        assertEquals(findMenu.getTitle(), createMenuRequest.getTitle());
        assertEquals(findMenu.getState(), createMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenuRequest.getUserId());
        assertEquals(findMenu.getParent(), null);
        assertEquals(findMenu.getOrderId(), createMenuRequest.getOrderId());
    }

    @Test/*parent != null, orderId == null*/
    void createMenu3() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        Menu parent = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId()));
        MenuRequest.CreateMenuRequest createMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId());
//        when
        Menu menu = menuService.createMenu(createMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        Integer size = menuRepository.findAllByParent(parent).size() - 1;
        assertEquals(findMenu.getTitle(), createMenuRequest.getTitle());
        assertEquals(findMenu.getState(), createMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenuRequest.getUserId());
        assertEquals(findMenu.getParent().getId(), createMenuRequest.getParentId());
        assertEquals(findMenu.getOrderId(), size);
    }

    @Test/*parent != null, orderId != null*/
    void createMenu4() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        Menu parent = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId()));
        Menu order = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        MenuRequest.CreateMenuRequest createMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId())
                .setOrderId(order.getOrderId());
//        when
        Menu menu = menuService.createMenu(createMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        assertEquals(findMenu.getTitle(), createMenuRequest.getTitle());
        assertEquals(findMenu.getState(), createMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenuRequest.getUserId());
        assertEquals(findMenu.getParent().getId(), createMenuRequest.getParentId());
        assertEquals(findMenu.getOrderId(), createMenuRequest.getOrderId());
    }

    @Test/*parent == null, orderId == null, save 만*/
    void updateMenu1() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        Menu parent = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test Parent 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId()));
        menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test Order 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        Menu createMenu = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test New 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        User updateUser = userService.createUser(new UserRequest.CreateUserRequest().setName("수정자"));
        MenuRequest.UpdateMenuRequest updateMenuRequest = new MenuRequest.UpdateMenuRequest()
                .setTitle("테스트 메뉴")
                .setState(MenuType.OFF)
                .setUserId(updateUser.getId());
//        when
        Menu menu = menuService.updateMenu(createMenu.getId(), updateMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        assertEquals(findMenu.getTitle(), menu.getTitle());
        assertEquals(findMenu.getState(), menu.getState());
        assertEquals(findMenu.getRegisterUser().getId(), menu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), menu.getUpdateUser().getId());
        assertEquals(findMenu.getParent().getId(), menu.getParent().getId());
        assertEquals(findMenu.getOrderId(), menu.getOrderId());
        assertEquals(findMenu.getTitle(), updateMenuRequest.getTitle());
        assertEquals(findMenu.getState(), updateMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), updateMenuRequest.getUserId());
        assertEquals(findMenu.getParent().getId(), createMenu.getParent().getId());
        assertEquals(findMenu.getOrderId(), createMenu.getOrderId());
    }

    @Test/*parent == null, orderId 기존과 같을 때, save 만*/
    void updateMenu2() {
//        given
        UserRequest.CreateUserRequest registerUserRequest = new UserRequest.CreateUserRequest().setName("등록자");
        User registerUser = userService.createUser(registerUserRequest);
        MenuRequest.CreateMenuRequest parentCreateMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId());
        Menu parent = menuService.createMenu(parentCreateMenuRequest);
        MenuRequest.CreateMenuRequest createMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId());
        menuService.createMenu(createMenuRequest);
        Menu createMenu = menuService.createMenu(createMenuRequest);
        UserRequest.CreateUserRequest updateUserRequest = new UserRequest.CreateUserRequest().setName("수정자");
        User updateUser = userService.createUser(updateUserRequest);
        MenuRequest.UpdateMenuRequest updateMenuRequest = new MenuRequest.UpdateMenuRequest()
                .setTitle("테스트 메뉴")
                .setState(MenuType.OFF)
                .setUserId(updateUser.getId())
                .setOrderId(createMenu.getOrderId());
//        when
        Menu menu = menuService.updateMenu(createMenu.getId(), updateMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        assertEquals(findMenu.getTitle(), menu.getTitle());
        assertEquals(findMenu.getState(), menu.getState());
        assertEquals(findMenu.getRegisterUser().getId(), menu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), menu.getUpdateUser().getId());
        assertEquals(findMenu.getParent().getId(), menu.getParent().getId());
        assertEquals(findMenu.getOrderId(), menu.getOrderId());
        assertEquals(findMenu.getTitle(), updateMenuRequest.getTitle());
        assertEquals(findMenu.getState(), updateMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), updateMenuRequest.getUserId());
        assertEquals(findMenu.getParent().getId(), createMenu.getParent().getId());
        assertEquals(findMenu.getOrderId(), updateMenuRequest.getOrderId());
    }

    @Test/*parent == null, orderId 기존과 다를 때, 기존 배열에서 삭제 후 새로운 배열에 추가, saveAll*/
    void updateMenu3() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        Menu parent = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test Parent 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId()));
        Menu order = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test Order 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        Menu createMenu = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test New 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        User updateUser = userService.createUser(new UserRequest.CreateUserRequest().setName("수정자"));
        MenuRequest.UpdateMenuRequest updateMenuRequest = new MenuRequest.UpdateMenuRequest()
                .setTitle("테스트 메뉴")
                .setState(MenuType.OFF)
                .setUserId(updateUser.getId())
                .setOrderId(order.getOrderId());
//        when
        Menu menu = menuService.updateMenu(createMenu.getId(), updateMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        assertEquals(findMenu.getTitle(), menu.getTitle());
        assertEquals(findMenu.getState(), menu.getState());
        assertEquals(findMenu.getRegisterUser().getId(), menu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), menu.getUpdateUser().getId());
        assertEquals(findMenu.getParent().getId(), menu.getParent().getId());
        assertEquals(findMenu.getOrderId(), menu.getOrderId());
        assertEquals(findMenu.getTitle(), updateMenuRequest.getTitle());
        assertEquals(findMenu.getState(), updateMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), updateMenuRequest.getUserId());
        assertEquals(findMenu.getParent().getId(), createMenu.getParent().getId());
        assertEquals(findMenu.getOrderId(), updateMenuRequest.getOrderId());
    }

    @Test/*parent == 기존과 같을 때, orderId == null, save 만*/
    void updateMenu4() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        Menu parent = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 부모 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId()));
        menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        Menu createMenu = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        User updateUser = userService.createUser(new UserRequest.CreateUserRequest().setName("수정자"));
        MenuRequest.UpdateMenuRequest updateMenuRequest = new MenuRequest.UpdateMenuRequest()
                .setTitle("테스트 메뉴")
                .setState(MenuType.OFF)
                .setUserId(updateUser.getId())
                .setParentId(createMenu.getParent().getId());
//        when
        Menu menu = menuService.updateMenu(createMenu.getId(), updateMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        assertEquals(findMenu.getTitle(), menu.getTitle());
        assertEquals(findMenu.getState(), menu.getState());
        assertEquals(findMenu.getRegisterUser().getId(), menu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), menu.getUpdateUser().getId());
        assertEquals(findMenu.getParent().getId(), menu.getParent().getId());
        assertEquals(findMenu.getOrderId(), menu.getOrderId());
        assertEquals(findMenu.getTitle(), updateMenuRequest.getTitle());
        assertEquals(findMenu.getState(), updateMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), updateMenuRequest.getUserId());
        assertEquals(findMenu.getParent().getId(), updateMenuRequest.getParentId());
        assertEquals(findMenu.getOrderId(), createMenu.getOrderId());
    }

    @Test/*parent == 기존과 같을 때, orderId 기존과 같을 때, save 만*/
    void updateMenu5() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        Menu parent = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test Parent 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId()));
        menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        Menu createMenu = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        User updateUser = userService.createUser(new UserRequest.CreateUserRequest().setName("수정자"));
        MenuRequest.UpdateMenuRequest updateMenuRequest = new MenuRequest.UpdateMenuRequest()
                .setTitle("테스트 메뉴")
                .setState(MenuType.OFF)
                .setUserId(updateUser.getId())
                .setParentId(createMenu.getParent().getId())
                .setOrderId(createMenu.getOrderId());
//        when
        Menu menu = menuService.updateMenu(createMenu.getId(), updateMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        assertEquals(findMenu.getTitle(), menu.getTitle());
        assertEquals(findMenu.getState(), menu.getState());
        assertEquals(findMenu.getRegisterUser().getId(), menu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), menu.getUpdateUser().getId());
        assertEquals(findMenu.getParent().getId(), menu.getParent().getId());
        assertEquals(findMenu.getOrderId(), menu.getOrderId());
        assertEquals(findMenu.getTitle(), updateMenuRequest.getTitle());
        assertEquals(findMenu.getState(), updateMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), updateMenuRequest.getUserId());
        assertEquals(findMenu.getParent().getId(), updateMenuRequest.getParentId());
        assertEquals(findMenu.getOrderId(), updateMenuRequest.getOrderId());
    }

    @Test/*parent == 기존과 같을 때, orderId 기존과 다를 때, 기존 배열에서 삭제 후 새로운 배열에 추가, save All*/
    void updateMenu6() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        Menu parent = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test Parent 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId()));
        Menu order = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        Menu createMenu = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        User updateUser = userService.createUser(new UserRequest.CreateUserRequest().setName("수정자"));
        MenuRequest.UpdateMenuRequest updateMenuRequest = new MenuRequest.UpdateMenuRequest()
                .setTitle("테스트 메뉴")
                .setState(MenuType.OFF)
                .setUserId(updateUser.getId())
                .setParentId(createMenu.getParent().getId())
                .setOrderId(order.getOrderId());
//        when
        Menu menu = menuService.updateMenu(createMenu.getId(), updateMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        assertEquals(findMenu.getTitle(), menu.getTitle());
        assertEquals(findMenu.getState(), menu.getState());
        assertEquals(findMenu.getRegisterUser().getId(), menu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), menu.getUpdateUser().getId());
        assertEquals(findMenu.getParent().getId(), menu.getParent().getId());
        assertEquals(findMenu.getOrderId(), menu.getOrderId());
        assertEquals(findMenu.getTitle(), updateMenuRequest.getTitle());
        assertEquals(findMenu.getState(), updateMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), updateMenuRequest.getUserId());
        assertEquals(findMenu.getParent().getId(), updateMenuRequest.getParentId());
        assertEquals(findMenu.getOrderId(), updateMenuRequest.getOrderId());
    }

    @Test/*parent == 기존과 다를 때, orderId == null, Parent 변경, 맨 뒤에 save*/
    void updateMenu7() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("등록자"));
        Menu parent = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test Parent 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId()));
        menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        Menu createMenu = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId()));
        Menu newParent = menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test New Parent 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId()));
        menuService.createMenu(new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(newParent.getId()));
        User updateUser = userService.createUser(new UserRequest.CreateUserRequest().setName("수정자"));
        MenuRequest.UpdateMenuRequest updateMenuRequest = new MenuRequest.UpdateMenuRequest()
                .setTitle("테스트 메뉴")
                .setState(MenuType.OFF)
                .setUserId(updateUser.getId())
                .setParentId(newParent.getId());
//        when
        Menu menu = menuService.updateMenu(createMenu.getId(), updateMenuRequest);
//        then
        Menu findMenu = menuRepository.findById(menu.getId()).get();
        Integer orderId = menuRepository.findAllByParent(findMenu.getParent()).size() - 1;
        assertEquals(findMenu.getTitle(), menu.getTitle());
        assertEquals(findMenu.getState(), menu.getState());
        assertEquals(findMenu.getRegisterUser().getId(), menu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), menu.getUpdateUser().getId());
        assertEquals(findMenu.getParent().getId(), menu.getParent().getId());
        assertEquals(findMenu.getOrderId(), menu.getOrderId());
        assertEquals(findMenu.getTitle(), updateMenuRequest.getTitle());
        assertEquals(findMenu.getState(), updateMenuRequest.getState());
        assertEquals(findMenu.getRegisterUser().getId(), createMenu.getRegisterUser().getId());
        assertEquals(findMenu.getUpdateUser().getId(), updateMenuRequest.getUserId());
        assertEquals(findMenu.getParent().getId(), updateMenuRequest.getParentId());
        assertEquals(findMenu.getOrderId(), orderId);
    }

    @Test
    void deleteMenu1() {
//        given
        UserRequest.CreateUserRequest registerUserRequest = new UserRequest.CreateUserRequest().setName("Test 이름");
        User registerUser = userService.createUser(registerUserRequest);
        MenuRequest.CreateMenuRequest parentCreateMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId());
        Menu parent = menuService.createMenu(parentCreateMenuRequest);
        MenuRequest.CreateMenuRequest orderCreateMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId());
        Menu order = menuService.createMenu(orderCreateMenuRequest);
        MenuRequest.CreateMenuRequest createMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId())
                .setOrderId(order.getOrderId());
        Menu menu = menuService.createMenu(createMenuRequest);
//        when
        Menu deleteMenu = menuService.deleteMenu(menu.getId());
//        then
        boolean findMenu = menuRepository.findById(menu.getId()).isPresent();
        assertEquals(deleteMenu.getId(), menu.getId());
        assertEquals(findMenu, false);
    }

    @Test
    void deleteMenu2() {
//        given
        UserRequest.CreateUserRequest registerUserRequest = new UserRequest.CreateUserRequest().setName("Test 이름");
        User registerUser = userService.createUser(registerUserRequest);
        MenuRequest.CreateMenuRequest parentCreateMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId());
        Menu parent = menuService.createMenu(parentCreateMenuRequest);
        MenuRequest.CreateMenuRequest orderCreateMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId());
        Menu order = menuService.createMenu(orderCreateMenuRequest);
        MenuRequest.CreateMenuRequest createMenuRequest = new MenuRequest.CreateMenuRequest()
                .setTitle("Test 메뉴")
                .setState(MenuType.ON)
                .setUserId(registerUser.getId())
                .setParentId(parent.getId())
                .setOrderId(order.getOrderId());
        Menu menu = menuService.createMenu(createMenuRequest);
//        when
        Menu deleteMenu = menuService.deleteMenu(order.getId());
//        then
        boolean findMenu = menuRepository.findById(order.getId()).isPresent();
        assertEquals(deleteMenu.getId(), order.getId());
        assertEquals(findMenu, false);
    }
}