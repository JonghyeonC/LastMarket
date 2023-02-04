package edu.ssafy.lastmarket;

import edu.ssafy.lastmarket.domain.entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestUtils {

    public static List<Category> getCategoryList() {
        ArrayList<Category> categoryArraList = new ArrayList<>();
        categoryArraList.add(new Category(1L, CategoryName.BOOK));
        categoryArraList.add(new Category(2L, CategoryName.CAMPING));
        return categoryArraList;
    }

    public static Member getMember() {
        Member member = Member.builder()
                .id(1L)
                .nickname("nick")
                .profile(new Image("imageName", "imageURL"))
                .lifestyle(Lifestyle.MINIMAL)
                .username("asdf")
                .location(getLocation())
                .build();
        return member;
    }

    public static Optional<Member> getMemberOptional(Member member) {
        return Optional.of(member);
    }

    public static MemberCategory getMemberCategory(Member member, Category category) {
        return new MemberCategory(member, category);
    }

    public static Category getCategory() {
        return new Category(1L, CategoryName.BOOK);
    }

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, CategoryName.BOOK));
        categories.add(new Category(2L, CategoryName.CAMPING));

        return categories;

    }

    public static List<CategoryName> getCategoryNames() {
        List<CategoryName> categoryNames = new ArrayList<>();
        categoryNames.add(CategoryName.CAMPING);
        categoryNames.add(CategoryName.BOOK);

        return categoryNames;

    }

    public static List<MemberCategory> getMemberCategories(Member member, List<CategoryName> categoryNames) {

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < categoryNames.size(); i++) {
            categories.add(new Category((long) i, categoryNames.get(i)));
        }

        List<MemberCategory> result = new ArrayList<>();
        for (int i = 0; i < categoryNames.size(); i++) {
            MemberCategory memberCategory = new MemberCategory((long) i, member, categories.get(i));
            result.add(memberCategory);
        }

        return result;
    }

    public static Product getProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("title");
        product.setContent("content");
        product.setLocation(getLocation());
        product.setDealState(DealState.DEFAULT);
        product.setLiveTime(LocalDateTime.now());
        product.setSeller(getMember());
        product.setFavoriteCnt(0);
        product.setStartingPrice(100000L);
        product.setInstantPrice(120000L);
        product.setImages(new ArrayList<>());
        return product;
    }

    public static Favorite getFavorite() {
        return new Favorite(1L, getMember(), getProduct());
    }

    public static Location getLocation() {
        return new Location("1111010300", "서울특별시", "종로구", "궁정동");

    }

    public static Trade getTrade() {
        Member seller = Member.builder()
                .id(1L)
                .nickname("seller")
                .profile(new Image("imageName", "imageURL"))
                .lifestyle(Lifestyle.MINIMAL)
                .username("seller")
                .location(getLocation())
                .build();

        Member buyer = Member.builder()
                .id(1L)
                .nickname("buyer")
                .profile(new Image("imageName", "imageURL"))
                .lifestyle(Lifestyle.MINIMAL)
                .username("buyer")
                .location(getLocation())
                .build();

        return Trade.builder()
                .id(1L)
                .buyer(buyer)
                .seller(seller)
                .product(getProduct())
                .build();
    }
}
