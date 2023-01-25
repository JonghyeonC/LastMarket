package edu.ssafy.lastmarket;

import edu.ssafy.lastmarket.domain.eneity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestUtils {

    public static List<Category> getCategoryList(){
        ArrayList<Category> categoryArraList= new ArrayList<>();
        categoryArraList.add(new Category(1L, CategoryName.BOOK));
        categoryArraList.add(new Category(2L, CategoryName.CAMPING));
        return categoryArraList;
    }

    public static Member getMember(){
        Member member = Member.builder()
                .id(1L)
                .nickname("nick")
                .job(Job.PROGRAMMER)
                .username("asdf")
                .build();
        return member;
    }

    public static Optional<Member> getMemberOptional(Member member){
        return Optional.of(member);
    }

    public static MemberCategory getMemberCategory(Member member, Category category){
        return new MemberCategory(member, category);
    }

    public static Category getCategory(){
        return new Category(1L,CategoryName.BOOK);
    }

    public static List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L,CategoryName.BOOK));
        categories.add(new Category(2L,CategoryName.CAMPING));

        return categories;

    }

    public static List<MemberCategory> getMemberCategories(Member member, List<Category> categories){

        List<MemberCategory> result = new ArrayList<>();
        for (int i=0;i<categories.size();i++) {
            MemberCategory memberCategory = new MemberCategory((long) i,member,categories.get(i));
            result.add(memberCategory);
        }

        return result;
    }











}
