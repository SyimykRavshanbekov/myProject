package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Company;
import peaksoft.service.CompanyService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CompanyController {

    private final CompanyService service;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.service = companyService;
    }


    @GetMapping("/getAllCompanies")
    public String getCompanies(Model model) {
        List<Company> companies = service.getAllCompanies();
        model.addAttribute("companies", companies);
        return "/company/all_companies";
    }

    @GetMapping("/addCompany")
    public String addCompany(Model model) {
        model.addAttribute("company", new Company());
        return "/company/addCompany";
    }

    @PostMapping("/saveCompany")
    public String saveCompany(@ModelAttribute("company") @Valid Company company, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "/company/addCompany";
        }

        service.addCompany(company);
        return "redirect:/getAllCompanies";
    }

    @GetMapping("updateCompany")
    public String updateCompany(@RequestParam("companyId") Long id, Model model) {
        Company company = service.getCompanyById(id);
        model.addAttribute("company", company);
        return "/company/updateCompany";
    }

    @PostMapping("/updateCompany")
    public String saveUpdateCompany(@ModelAttribute("company") @Valid Company company, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "/company/updateCompany";
        }
        service.updateCompany(company);
        return "redirect:/getAllCompanies";
    }

    @RequestMapping("/deleteCompany")
    public String deleteCompany(@RequestParam("companyId") Long id) {
        service.deleteCompany(service.getCompanyById(id));
        return "redirect:/getAllCompanies";
    }
}