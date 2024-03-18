package com.sistema_login.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema_login.demo.models.User;
import com.sistema_login.demo.repositorio.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping({ "/" })
	public String loginForm() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		User user = userRepository.findByUsername(username);
		if (user != null && user.getPassword().equals(password)) {

			String nome = user.getNome();
			model.addAttribute("nome", nome);
			return "welcome";
			
		} else {
			return "redirect:/register";
		}
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		if (model.containsAttribute("successMessage")) {
			model.addAttribute("successMessage", model.getAttribute("successMessage"));
		}
		return "register";
	}

	@PostMapping("/register")
	public String register(@RequestParam String nome, @RequestParam String username, @RequestParam String password,
			@RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
		if (!password.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("error", "Senhas não conferem!");
			return "redirect:/register";
		}

		
	User existingUser = userRepository.findByUsername(username);
		if (existingUser != null) {
			redirectAttributes.addFlashAttribute("error", "Nome de usuário já está em uso!");
			return "redirect:/register";
		} 

		
		User user = new User();
		user.setNome(nome);
		user.setUsername(username);
		user.setPassword(password);
		userRepository.save(user);

		
		redirectAttributes.addFlashAttribute("successMessage", "Cadastro realizado com sucesso!");

		
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout() {
		// Lógica para realizar o logout
		return "login";
	}
}