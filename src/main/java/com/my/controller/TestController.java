package com.my.controller;

import javax.annotation.Resource;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.model.Address;
import com.my.model.AddressCheckResult;

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
		// Address address1 = new Address();
		// address1.setPostcode("2017");
		// AddressCheckResult result1 = new AddressCheckResult();
		// kieSession.insert(address1);
		// kieSession.insert(result1);
		// AddressFilter addressFilter = new AddressFilter(address1);
		// int ruleFiredCount1 = kieSession.fireAllRules(addressFilter);
		// System.out.println("触发了" + ruleFiredCount1 + "条规则");
	}
}