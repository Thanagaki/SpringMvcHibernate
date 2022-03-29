package web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;


import java.util.ArrayList;
import java.util.List;

@RequestMapping("/users")
@Controller
public class HelloController {

	private final UserService userService;

	public HelloController(UserService userService) {
		this.userService = userService;
	}



	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		return "index";
	}

	@GetMapping()
	public String get (Model model) {
		model.addAttribute("users", userService.index());
		return "UserIndex";
	}

	@GetMapping("/{id}")
	public String getById(@PathVariable("id") long id, Model model ) {
		model.addAttribute("userId", userService.getUserById(id));
		return "UserId";
	}
	//Форма для создания нового ползователя
	@GetMapping("/new")
		public String newPerson(@ModelAttribute("newUser") User emptyUser) {
		return "new";
	}
	// Метод принимающий форму нового пользователя
	// и делающий перевод на страницу со всеми пользователями

	@PostMapping()
	public String create(@ModelAttribute("newUser") User user) {
		userService.create(user);
		return "redirect:/users";
	}
	//Форма для редактирования выбранного пользователья
	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") long id) {
		model.addAttribute("userUpdate", userService.getUserById(id));
		return "edit";
	}
	//Метод принимающий форму отредактированного пользователя
	// и делающий перевод на стриницу со всеми пользователями
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("userUpdate") User user, @PathVariable("id") long id) {
		userService.update(id,user);
		return "redirect:/users";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") long id, Model model) {
		userService.delete(id);
		return "redirect:/users";
	}

}