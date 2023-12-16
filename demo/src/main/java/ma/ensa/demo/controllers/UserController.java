package ma.ensa.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import ma.ensa.demo.entities.Utilisatuer;
import ma.ensa.demo.repositories.UtilisateurRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    @Autowired
    private UtilisateurRepository us;

    @GetMapping("/")
    public String accueil(Model model) {
        model.addAttribute("users", us.findAll());
        return "accueil";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("user", new Utilisatuer());
        return "add";
    }

    @PostMapping("/ajout")
    public String add(Model model, @Valid Utilisatuer user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add";
        } else {
            us.save(user);
            model.addAttribute("users", us.findAll());
            return "accueil";
        }
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Utilisatuer user = us.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Utilisatuer user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update";
        }

        us.save(user);
        model.addAttribute("users", us.findAll());
        return "accueil";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Utilisatuer user = us.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        us.delete(user);
        model.addAttribute("users", us.findAll());
        return "accueil";
    }

}
