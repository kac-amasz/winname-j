package com.kac.winname;

public class App {
	public static void main(String[] args) throws Exception {
		System.out.println(new WindowFinderFactory().getWindowFinder().getActiveWindow());
	}
}
