package Junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import methods.Money;
import methods.MoneyBag;

public class MoneyBagTest {
	private Money m12nis;
	private Money m16nis;
	private Money m28nis;
	private Money m14chf;
	private MoneyBag m1214nischf;

	@Before
	public void setUp() throws Exception {
		m12nis = new Money(12, "NIS");
		m16nis = new Money(16, "NIS");
		m28nis = new Money(28, "NIS");
		m14chf = new Money(14, "CHF");
		m1214nischf = new MoneyBag(m12nis, m14chf);
	}

	@Test
	public void testAddMoney() {

		// case1: adding positive amount of money
		MoneyBag expected = new MoneyBag(new Money(24, "NIS"), m14chf);
		MoneyBag result = (MoneyBag) m1214nischf.addMoney(m12nis);
		assertEquals(expected, result);

		// case2: adding negative amount of money
		MoneyBag expected1 = new MoneyBag(new Money(6, "NIS"), m14chf);
		MoneyBag result1 = (MoneyBag) m1214nischf.addMoney(new Money(-6, "NIS"));
		assertEquals(expected1, result1);

		// case3: adding 0 amount of money
		MoneyBag expected2 = new MoneyBag(m12nis, m14chf);
		MoneyBag result2 = (MoneyBag) m1214nischf.addMoney(new Money(0, "NIS"));
		assertEquals(expected2, result2);

		// case4: adding a third currency
		MoneyBag expected3 = new MoneyBag(new Money[] { m12nis, m14chf, new Money(7, "USD") });
		MoneyBag result3 = (MoneyBag) m1214nischf.addMoney(new Money(7, "USD"));
		assertEquals(expected3, result3);

	}

	@Test
	public void testContains() {

		// case1: check if the moneybag contains an inserted currency 
		assertTrue(m1214nischf.contains(m12nis));

		// case2: check if only one currency is in the moneybag
		MoneyBag expected = new MoneyBag(m12nis, m16nis);
		assertTrue(expected.contains(m28nis));

		// case3: check if contains Negative amount in the moneybag
		MoneyBag expected1 = new MoneyBag(new Money(-6, "NIS"), m14chf);
		assertTrue(expected1.contains(new Money(-6, "NIS")));

		// case4: check if contains 0 is in the moneybag
		MoneyBag expected2 = new MoneyBag(new Money(0, "NIS"), m14chf);
		assertTrue(expected2.contains(new Money(0, "NIS")));
	}
}
