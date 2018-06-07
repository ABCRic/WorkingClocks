package me.abcric.bukkit.workingclocks;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class WorkingClocksTest {
	@DataProvider
	public Object[][] test24hData() {
		return new Object[][] {
			{ 24000, "6:00" },
			{ 0, "6:00" },
			{ 1000, "7:00" },
			{ 6000, "12:00" },
			{ 22550, "4:33" },
			{ 12967, "18:58" },
			{ 18000, "0:00" }
		};
	}

	@Test(dataProvider = "test24hData")
	public void test24h(long ticks, String output) {
		assertEquals(WorkingClocks.ticksTo24h(ticks), output);
	}

	@DataProvider
	public Object[][] test12hData() {
		return new Object[][] {
			{ 24000, "6:00 am" },
			{ 0, "6:00 am" },
			{ 1000, "7:00 am" },
			{ 6000, "12:00 pm" },
			{ 22550, "4:33 am" },
			{ 12967, "6:58 pm" },
			{ 18000, "12:00 am" }
		};
	}

	@Test(dataProvider = "test12hData")
	public void test12h(long ticks, String output) {
		assertEquals(WorkingClocks.ticksTo12h(ticks), output);
	}
}
