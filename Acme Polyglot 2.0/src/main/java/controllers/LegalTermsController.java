package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/legalTerms")
public class LegalTermsController extends AbstractController {

	// Constructors -----------------------------------------------------------
	public LegalTermsController() {
		super();
	}

	@RequestMapping(value = "/legalTerms", method = RequestMethod.GET)
	public ModelAndView legalTerms() {
		ModelAndView result;

		result = new ModelAndView("legalTerms/legalTerms");
		result.addObject("legalTerms", true);

		return result;
	}

	@RequestMapping(value = "/cookies", method = RequestMethod.GET)
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("legalTerms/legalTerms");
		result.addObject("cookies", true);

		return result;
	}

	@RequestMapping(value = "/legalInformation", method = RequestMethod.GET)
	public ModelAndView legalInformation() {
		ModelAndView result;

		result = new ModelAndView("legalTerms/legalTerms");
		result.addObject("legalInformation", true);

		return result;
	}

	@RequestMapping(value = "/personalData", method = RequestMethod.GET)
	public ModelAndView personalData() {
		ModelAndView result;

		result = new ModelAndView("legalTerms/legalTerms");
		result.addObject("personalData", true);

		return result;
	}

}
