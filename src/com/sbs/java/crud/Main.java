package com.sbs.java.crud;

import java.util.Scanner;    //ctrl + shift + o

public class Main {
	public static void main(String[] args) {
		System.out.println("==���α׷� ����==");

		Scanner sc = new Scanner(System.in);
		System.out.printf("��ɾ�) ");
		String command = sc.nextLine().trim();

		System.out.printf("�Էµ� ��ɾ� : %s\n", command);

//		System.out.printf("��ɾ�) ");
//		int number = sc.nextInt();
//
//		System.out.printf("�Էµ� ���� : %d\n", number);

		sc.close();

		System.out.println("==���α׷� ��==");
	}
}