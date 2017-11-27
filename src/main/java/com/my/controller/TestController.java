package com.my.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.model.Address;
import com.my.model.AddressCheckResult;
import com.my.myfilter.AddressFilter;

@RequestMapping("/test")
@Controller
public class TestController {

	@Resource
	private KieSession kieSession;

	@ResponseBody
	@RequestMapping("/address")
	public void test() {
		Address address = new Address();
		address.setPostcode("99425");

		AddressCheckResult result = new AddressCheckResult();
		FactHandle f = kieSession.insert(address);
		FactHandle f1 = kieSession.insert(result);
		int ruleFiredCount = kieSession.fireAllRules();
		System.out.println("触发了" + ruleFiredCount + "条规则");
		if (result.isPostCodeResult()) {
			System.out.println("规则校验通过");
		}
		kieSession.delete(f);
		kieSession.delete(f1);
		// 这里添加过滤器
		Address address1 = new Address();
		address1.setPostcode("2017");
		AddressCheckResult result1 = new AddressCheckResult();
		FactHandle f2 = kieSession.insert(address1);
		FactHandle f3 = kieSession.insert(result1);
		AddressFilter addressFilter = new AddressFilter(address1);
		int ruleFiredCount1 = kieSession.fireAllRules(addressFilter);
		System.out.println("触发了" + ruleFiredCount1 + "条规则");
		kieSession.delete(f2);
		kieSession.delete(f3);
	}

	@ResponseBody
	@RequestMapping("/address1")
	public void test1() {
		Address address = new Address();
		address.setPostcode("99425");
		List<String> list = new ArrayList<String>();
		AddressCheckResult result = new AddressCheckResult();
		FactHandle f = kieSession.insert(address);
		FactHandle f1 = kieSession.insert(result);
		kieSession.setGlobal("myGlobalList", list);
		int ruleFiredCount = kieSession.fireAllRules();
		System.out.println("触发了" + ruleFiredCount + "条规则");
		if (result.isPostCodeResult()) {
			System.out.println("规则校验通过");
		}
		System.out.println(list.toString());
		kieSession.delete(f);
		kieSession.delete(f1);

	}
	
	@ResponseBody
	@RequestMapping("/rule")
	public void rule() {
		Address address = new Address();
		address.setPostcode("99425");
		FactHandle f = kieSession.insert(address);
		int ruleFiredCount = kieSession.fireAllRules();
		System.out.println("触发了" + ruleFiredCount + "条规则");
		kieSession.delete(f);

	}
	
	@ResponseBody
	@RequestMapping("/function")
	public void function() {
		Address address = new Address();
		address.setPostcode("function");
		FactHandle f = kieSession.insert(address);
		int ruleFiredCount = kieSession.fireAllRules();
		System.out.println("触发了" + ruleFiredCount + "条规则");
		kieSession.delete(f);

	}
}