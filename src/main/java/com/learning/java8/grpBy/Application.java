package com.learning.java8.grpBy;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summingInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class Application {

	public static void main(String[] args) {

		List<BlogPost> blogPosts = prepBlogPost();

		blogPosts.forEach((blog) -> System.out.println("Auther : "+blog.getAuthor() + " - Likes : " + blog.getLikes()));

		grpByMax(blogPosts);
		grpBySum(blogPosts);
		grpByAvg(blogPosts);

	}


	// Find BlogPostType with maximum likes ; it's like grp by with max;
	private static void grpByMax(List<BlogPost> blogPosts) {
		
		Map<BlogPostType, Optional<BlogPost>> map = blogPosts.stream()
				         .collect(groupingBy(BlogPost::getType, maxBy(comparingInt(BlogPost::getLikes))));

		System.out.println("GrpByMax==START====");
		map.entrySet().stream().forEach(System.out::println);
		System.out.println("GrpByMax==END====");
	}
	
	// Find Author's total likes; it's like grp by with avg;
	private static void grpByAvg(List<BlogPost> blogPosts) {
		
		Map<String, Double> grpByAuthWithAvgLikes = blogPosts.stream()
				.collect(groupingBy(BlogPost::getAuthor, averagingInt(BlogPost::getLikes)));
		grpByAuthWithAvgLikes.forEach((k, v) -> System.out.println(k + " - " + v));
	}
	
	private static void grpBySum(List<BlogPost> blogPosts) {
		// Find Author's total likes; it's like grp by with sum;
		Map<String, Integer> mapWithLikes = blogPosts.stream()
				.collect(groupingBy(BlogPost::getAuthor, summingInt(BlogPost::getLikes)));

		System.out.println("GrpBySum==START====");
		mapWithLikes.entrySet().stream().forEach(System.out::println);
		Optional<Entry<String, Integer>> maxLikedBlog = mapWithLikes.entrySet().stream()
		.max(comparingInt(Entry::getValue));
		System.out.println("Max liked blog is = "+maxLikedBlog);
		System.out.println("GrpBySum==END====");
		
		
	}

	private static List<BlogPost> prepBlogPost() {
		List<BlogPost> blogPosts = new ArrayList<BlogPost>();
		blogPosts.add(new BlogPost("Java - Stream API", "Vijay Patel", BlogPostType.GUIDE, 4000));
		blogPosts.add(new BlogPost("Java - Lambda Expression", "Vijay Patel", BlogPostType.GUIDE, 12908));
		blogPosts.add(new BlogPost("Apache Spark", "Martin Flower", BlogPostType.NEWS, 4000));
		blogPosts.add(new BlogPost("Apache Hadoop", "Martin Flower", BlogPostType.NEWS, 400));
		blogPosts.add(new BlogPost("Design Pattern", "Vijay Patel", BlogPostType.GUIDE, 4000));
		blogPosts.add(new BlogPost("Kafka with Spring", "Vijay Patel", BlogPostType.GUIDE, 4000));
		blogPosts.add(new BlogPost("Java - 12", "Vijay Patel", BlogPostType.GUIDE, 4000));
		blogPosts.add(new BlogPost("Spring JPA", "Vijay Patel", BlogPostType.REVIEW, 4000));
		blogPosts.add(new BlogPost("Spring in Action", "Vijay Patel", BlogPostType.REVIEW, 7600));
		blogPosts.add(new BlogPost("Java - Stream API", "Ajay Patel", BlogPostType.GUIDE, 4000));
		blogPosts.add(new BlogPost("Java - Stream API", "James Cameroon", BlogPostType.GUIDE, 7890));
		return blogPosts;

	}

}
