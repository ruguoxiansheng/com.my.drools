package com.my.myfilter;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import com.my.model.Address;

public class AddressFilter implements AgendaFilter {

	// 传进来的是Address对象，把postcode以2017年开头的执行规则，否则不执行
	private final Address address;

	public AddressFilter(Address address) {
		this.address = address;
	}

	@Override
	public boolean accept(Match match) {
		return match.getRule().getName().contains(address.getPostcode());
	}

}
