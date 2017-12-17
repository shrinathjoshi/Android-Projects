package com.example.hp.recipeapp.mock;


import com.example.hp.recipeapp.model.GlobaDataHolder;
import com.example.hp.recipeapp.model.entities.Product;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/*
 * This class serve as fake server and provides dummy product and category with real Image Urls taken from google
 */
public class FakeWebServer {

	private static FakeWebServer fakeServer;

	void initiateFakeServer() {



	}



	public void getAllCakes() {

		ConcurrentHashMap<String, ArrayList<Product>> productMap = new ConcurrentHashMap<String, ArrayList<Product>>();

		ArrayList<Product> productlist = new ArrayList<Product>();

		// cakes
		productlist
				.add(new Product(
						"Chocolate cake 1 ",
						"short description",
						"details .....",
						"5000",
						"1000",
						"4000",
						"0",
						"https://s-media-cache-ak0.pinimg.com/736x/b4/01/47/b40147865378fca69dfcdc883f7eeb4c.jpg",
						""));

		productlist
				.add(new Product(
						"Chocolate cake 1 ",
						"short description",
						"details .....",
						"5000",
						"1000",
						"4000",
						"0",
						"https://s-media-cache-ak0.pinimg.com/736x/b4/01/47/b40147865378fca69dfcdc883f7eeb4c.jpg",
						""));

		productlist
				.add(new Product(
						"Chocolate cake 1 ",
						"short description",
						"details .....",
						"5000",
						"1000",
						"4000",
						"0",
						"https://s-media-cache-ak0.pinimg.com/736x/b4/01/47/b40147865378fca69dfcdc883f7eeb4c.jpg",
						""));

		productlist
				.add(new Product(
						"Chocolate cake 1 ",
						"short description",
						"details .....",
						"5000",
						"1000",
						"4000",
						"0",
						"https://s-media-cache-ak0.pinimg.com/736x/b4/01/47/b40147865378fca69dfcdc883f7eeb4c.jpg",
						""));

		productlist
				.add(new Product(
						"Chocolate cake 1 ",
						"short description",
						"details .....",
						"5000",
						"1000",
						"4000",
						"0",
						"https://s-media-cache-ak0.pinimg.com/736x/b4/01/47/b40147865378fca69dfcdc883f7eeb4c.jpg",
						""));

		productMap.put("cakes", productlist);


		GlobaDataHolder.getGlobaDataHolder().setProductMap(productMap);

	}


	public static FakeWebServer getFakeWebServer() {

		if (null == fakeServer) {
			fakeServer = new FakeWebServer();
		}
		return fakeServer;
	}

	public void getAllProducts(String productCategory) {
		if(productCategory.equals("cakes"))
		getAllCakes();
	}

}
