package my.site.dealsite;

import java.util.ArrayList;
import java.util.StringTokenizer;
import my.site.dealsite.DAO.*;
import my.site.dealsite.Controller.*;
import my.site.dealsite.Enum.*;
import my.site.dealsite.Service.*;
import my.site.dealsite.VO.*;

public class Test {
	
	public void makeSamplePosts()  {
		Post post=null;
		String sort2[][] ={ {"이어폰","노트북","데스크탑","모니터"}, {"세탁기","에어컨","냉장고","세탁기"},{"축구","농구","스키","헬스"},
				{"상의","하의","신발","지갑"},{"학습","소설","잡지","요리"}};
		String sort1[] = {"컴퓨터","가전","스포트,레저","의류","도서"};
		//Math.random()*100;
		for(int i=0; i<130; i++) {
			//Integer.toString((int)(Math.random()*100))
			post=new Post();
			post.setTitle(Integer.toString((int)(Math.random()*100)));
			post.setSort1(sort1[i%4]);
			post.setSort2(sort2[i%4][((int)(Math.random()*10)%4)]);
			post.setContent(Integer.toString((int)(Math.random()*100)));
			post.setMainImage("noNameImage.jpeg");
			post.setWriterId("tjrghfeel");
			post.setWriterNum(1);
			post.setPrice(10000);
			post.setOpen(false);
			
			Dao.insertVO(post);
			try {
			Thread.sleep(1000);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
    /*public static void main(String[] args) {
    	Post post=null;
		String sort[][] ={ {"이어폰","노트북","데스크탑","모니터"}, {"세탁기","에어컨","냉장고","세탁기"},{"축구","농구","스키","헬스"},
				{"상의","하의","신발","지갑"},{"학습","소설","잡지","요리"}};
		//Math.random()*100;
		for(int i=0; i<100; i++) {
			System.out.println(Math.random()*100);
		}

    	return;
    }*/
}