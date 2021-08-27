package com.sbs.java.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static List<Article> articles; //위치 위로 바꾸고, //private은 외부에서 접근 금지라는 말.
	
	static {//생성자로 만들기 //static 생성자는 static으로 만들어야함 !
		 articles = new ArrayList<>(); //ArrayList 객체로 만든것
	}
	
	
	public static void main(String[] args) {//static은 오직 static끼리만 통신이 가능하다 !
		System.out.println("==프로그램 시작==");
		
		makeTestData();
		
		Scanner sc = new Scanner(System.in);
		//int lastArticleId = 0; 
		//첫번째가 0이면, 3개의 데이터를 생성한 다음, article write했을 때 4번이 되어야 하는데 다시 1이 됨.
		//articles.size(); 배열 안에 데이터가 몇 개인지 확인할 때 씀.

		while (true) {
			System.out.printf("명령어) ");
			String command = sc.nextLine().trim();
			if (command.length() == 0) {
				continue;
			}
			if (command.equals("system exit")) {
				break;
			}
			if (command.equals("article write")) {
				int id = articles.size() + 1;
				//lastArticleId = id; //int id를 articles.size()로 바꿨기 때문에 이건 필요X.
				String regDate = Util.getNowDateStr();
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, title, body, 0); //최초의 조회수는 0이니까 0.
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", id);
			} else if (command.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시물이 없습니다.");
					continue;
				}
				System.out.println("번호  |  조회 |  제목");
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);

					System.out.printf("%4d  |  %4d |  %s\n", article.id, article.hit, article.title);
				}

			} else if (command.startsWith("article detail ")) {
				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]); // "1" -> 1

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) { // 0 1 2 3 4
					Article article = articles.get(i);

					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}

				foundArticle.increaseHit();

				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("날짜 : %s\n", foundArticle.regDate);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);
				System.out.printf("조회 : %d\n", foundArticle.hit);
			} else if (command.startsWith("article modify ")) {
				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]); // "1" -> 1

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) { // 0 1 2 3 4
					Article article = articles.get(i);

					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}

				System.out.printf("새 제목 : ");
				String title = sc.nextLine();
				System.out.printf("새 내용 : ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.body = body;

				System.out.printf("%d번 게시물이 수정되었습니다.\n", foundArticle.id);
			} else if (command.startsWith("article delete ")) {
				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				int foundIndex = -1;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);

					if (article.id == id) {
						foundIndex = i;
						break;
					}
				}

				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}
				// size() => 3
				// index : 0 1 2
				// id : 1 2 3
				articles.remove(foundIndex);
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
			}

			else {
				System.out.printf("%s(은)는 존재하지 않는 명령어입니다.\n", command);
			}
		}

		sc.close();

		System.out.println("==프로그램 끝==");
	}

	private static void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		
		articles.add(new Article(1,Util.getNowDateStr(),"제목 1","내용 1",11));
		articles.add(new Article(1,Util.getNowDateStr(),"제목 2","내용 2",22));
		articles.add(new Article(1,Util.getNowDateStr(),"제목 3","내용 3",33));
	} //add로 데이터 3개 생성함. (제목, 내용, 조회수), 여기서 조회수 11,22,33 은 지정한 것...
}

//class makeTestData {
//이건 ?
//}

class Article {
	int id;
	String regDate;
	String title;
	String body;
	int hit;

	//아래 2개는 엄연히 다른 method, 매개변수의 개수나 타입이 다르기 때문에 '메서드 오버로딩' 한 것 !
	public Article(int id, String regDate, String title, String body) { //인자 4개짜리의 실행을 위해.
		this(id, regDate, title, body, 0); 
		//생성자가 다른 생성자에게 일을 떠밀 때 이렇게 써야 함 //ex) this();
	}
	
	public Article(int id, String regDate, String title, String body, int hit) { //인자 5개짜리의 실행을 위해.
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.hit = hit;
	}

	public void increaseHit() {
		hit++;
	}
}