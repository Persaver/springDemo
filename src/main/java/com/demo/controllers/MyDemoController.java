package com.demo.controllers;

import java.io.FileOutputStream;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.demo.models.account.Account;


@Controller
@SessionAttributes("aNewAccount")
public class MyDemoController {
	private static final Logger LOG = LoggerFactory.getLogger(MyDemoController.class);


	private String[] quotes = {"randomQuote","to be or not","Shakespeare told"};

	@ModelAttribute
	public void addAccountToModel(Model model){
		LOG.info("Making sure account object is on model");
		if(!model.containsAttribute("aNewAccount")){
			Account a = new Account();
			model.addAttribute("aNewAccount", a);
		}
	}
	@RequestMapping(value="/getCookie")
	public String getCookie(@CookieValue("myRandomCookie") String myCookie){
		LOG.info("Cookie retrived {}",myCookie);

		return "demoPage";
	}

	@RequestMapping(value="/addCookie")
	public String addCookie(HttpServletResponse response){

		//add a randomCookie
		response.addCookie(new Cookie("myRandomCookie", "aCookieAdded"));

		LOG.info("Cookie added");

		return "demoPage";
	}


	//	@ModelAttribute
	//	public void setUserDetails(@RequestParam("userName") String userName, Model model){
	//		model.addAttribute("userName", userName);
	//
	//		String role ="undefined";
	//
	//		if(userName.equals("Saver")){
	//			role = "Student";
	//		}
	//		else if(userName.equals("Adrien")){
	//			role = "Teacher";
	//		}
	//		else if(userName.equals("Ludo")){
	//			role ="Dean";
	//		}
	//
	//		model.addAttribute("userRole", role);
	//
	//		LOG.info("Model update with user information");
	//	}


	@RequestMapping(value="/getQuote",params="form=keisha", headers="X-API-KEY=456789")
	public String getRandomQuote(@RequestParam("userName") String userName,Model model) {

		int rand = new Random().nextInt(this.quotes.length);
		String randomQuote = this.quotes[rand];

		model.addAttribute("randomQuote",randomQuote);

		LOG.info("UserName is" + userName);
		model.addAttribute("userName", userName);

		return "quote";
	}

	@RequestMapping(value="/createAccount")
	public String createAccount(@Valid @ModelAttribute("aNewAccount") Account account, BindingResult result) {
		LOG.info(""+result.getErrorCount() + " target " + result.getTarget().getClass().getName());
		if(result.hasErrors()){
			LOG.info("Form has errors");
			return "createAccount";
		}
		LOG.info("Form validated");
		LOG.info("account " + account.getFirstName() + " " + account.getLastName() + " " + " " + account.getAge() + " " + account.getAddress() + " " + account.getEmail());
		return "createAccount";
	}

	@RequestMapping(value="/doCreate",method=RequestMethod.POST)
	public String doCreate(@ModelAttribute("aNewAccount") Account account){
		LOG.info("doCreate: account " + account.getFirstName() + " " + account.getLastName() + " " + " " + account.getAge() + " " + account.getAddress() + " " + account.getEmail());
		LOG.info("doCreate: Going off and creating actual account ");
		return "redirect:accConfirmation";
	}

	@RequestMapping(value="/accConfirmation")
	public String accountConfirmation(@ModelAttribute("aNewAccount") Account account){
		LOG.info("accountConfirmation " + account.getFirstName() + " " + account.getLastName() + " " + " " + account.getAge() + " " + account.getAddress() + " " + account.getEmail());
		return "accountCreated";
	}
	@RequestMapping(value="/accountCreated",method=RequestMethod.POST)
	public String performCreate(Account account){
		LOG.info("account " + account.getFirstName() + " " + account.getLastName() + " " + " " + account.getAge() + " " + account.getAddress() + " " + account.getEmail());
		return "accountCreated";
	}

	@RequestMapping(value="/myForm")
	public String myform(){

		return "myForm";
	}

	@RequestMapping(value="/handleForm")
	public String handleForm(@RequestParam("file") MultipartFile file){

		try{
			if(!file.isEmpty()){
				byte[] bytes = file.getBytes();
				FileOutputStream fos = new FileOutputStream("D:\\Users\\saperri\\dev\\download\\test.docx");
				fos.write(bytes);
				fos.close();
				LOG.info("File saved succesfully");
			}
			else{
				LOG.info("No file avaiable");
			}
		}
		catch(Exception e){
			LOG.info(e.getMessage());
		}

		return "operationComplete";
	}
}
