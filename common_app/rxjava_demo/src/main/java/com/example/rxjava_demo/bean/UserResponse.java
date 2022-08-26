package com.example.rxjava_demo.bean;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

	@SerializedName("gists_url")
	private String gistsUrl;

	@SerializedName("repos_url")
	private String reposUrl;

	@SerializedName("following_url")
	private String followingUrl;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("login")
	private String login;

	@SerializedName("type")
	private String type;

	@SerializedName("blog")
	private String blog;

	@SerializedName("subscriptions_url")
	private String subscriptionsUrl;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("site_admin")
	private boolean siteAdmin;

	@SerializedName("id")
	private double id;

	@SerializedName("public_repos")
	private double publicRepos;

	@SerializedName("gravatar_id")
	private String gravatarId;

	@SerializedName("organizations_url")
	private String organizationsUrl;

	@SerializedName("starred_url")
	private String starredUrl;

	@SerializedName("followers_url")
	private String followersUrl;

	@SerializedName("public_gists")
	private double publicGists;

	@SerializedName("url")
	private String url;

	@SerializedName("received_events_url")
	private String receivedEventsUrl;

	@SerializedName("followers")
	private double followers;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("events_url")
	private String eventsUrl;

	@SerializedName("html_url")
	private String htmlUrl;

	@SerializedName("following")
	private double following;

	@SerializedName("node_id")
	private String nodeId;

	public String getGistsUrl(){
		return gistsUrl;
	}

	public String getReposUrl(){
		return reposUrl;
	}

	public String getFollowingUrl(){
		return followingUrl;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getLogin(){
		return login;
	}

	public String getType(){
		return type;
	}

	public String getBlog(){
		return blog;
	}

	public String getSubscriptionsUrl(){
		return subscriptionsUrl;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public boolean isSiteAdmin(){
		return siteAdmin;
	}

	public double getId(){
		return id;
	}

	public double getPublicRepos(){
		return publicRepos;
	}

	public String getGravatarId(){
		return gravatarId;
	}

	public String getOrganizationsUrl(){
		return organizationsUrl;
	}

	public String getStarredUrl(){
		return starredUrl;
	}

	public String getFollowersUrl(){
		return followersUrl;
	}

	public double getPublicGists(){
		return publicGists;
	}

	public String getUrl(){
		return url;
	}

	public String getReceivedEventsUrl(){
		return receivedEventsUrl;
	}

	public double getFollowers(){
		return followers;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public String getEventsUrl(){
		return eventsUrl;
	}

	public String getHtmlUrl(){
		return htmlUrl;
	}

	public double getFollowing(){
		return following;
	}

	public String getNodeId(){
		return nodeId;
	}
}