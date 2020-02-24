package com.webjaguar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

class SetterForCompare{
    ArrayList<Integer> list;
    int index;
    
    
	/**
	 * @param list
	 * @param index
	 */
	public SetterForCompare(ArrayList<Integer> list, int index) {
		super();
		this.list = list;
		this.index = index;
	}
 
    
}

/**
 * @author RAMANI
 *
 */
public class MyApp {

	HashMap<Integer, HashSet<Integer>> userList; //user and followees
    HashMap<Integer, ArrayList<Integer>> userPostList; //user and post
    HashMap<Integer, Integer> postCounterList; //post and postCounter
    int postCouter; //post counter
    
    
    /** Initialize your data structure here. */
    public MyApp() {
    	userList = new HashMap<Integer, HashSet<Integer>>();
    	userPostList = new HashMap<Integer, ArrayList<Integer>>();
    	postCounterList = new HashMap<Integer, Integer>();
    }
    
	
   
    /** Compose a new post. */
	public void postMessage(int userId, int postId) {
        ArrayList<Integer> list = userPostList.get(userId);
        if(list==null){
            list = new ArrayList<Integer>();
            userPostList.put(userId, list);
        }
        list.add(postId);
        
        String msg = Messages.getString("MyApp.0")+userId+Messages.getString("MyApp.1")+postId; //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println(msg);
        
        postCounterList.put(postId, postCouter++);
        follow(userId, userId); //user follow ourself by automatically
    }
	
	/** Follower follows a followee. */
    public void follow(int followerId, int followeeId) {
        HashSet<Integer> set = userList.get(followerId);
        if(set==null){
            set = new HashSet<Integer>();
            userList.put(followerId, set);
        }
        set.add(followeeId);
        
        String msg = Messages.getString("MyApp.2")+followerId+Messages.getString("MyApp.3")+followeeId; //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println(msg);
    }
    
    /** Retrieve the 10 most recent post ids in the user's news feed. */
    public void getNewsFeed(int userId) {
        HashSet<Integer> set = userList.get(userId);
        if(set==null)
            return;
 
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
 
        //get all users' Post
        for(int uid: set){
            if(userPostList.get(uid)!=null && userPostList.get(uid).size()>0)
                lists.add(userPostList.get(uid)); 
        }
 
        ArrayList<Integer> newsFeed = new ArrayList<Integer>();
 
        //Comparator for compare post
        PriorityQueue<SetterForCompare> queue  = new PriorityQueue<SetterForCompare>(new Comparator<SetterForCompare>() {

			@Override
			public int compare(SetterForCompare o1, SetterForCompare o2) {
				
				// TODO Auto-generated method stub
				
				int result =  postCounterList.get(o2.list.get(o2.index))-postCounterList.get(o1.list.get(o1.index));
				return result;
			}
		});
        
        
        for(ArrayList<Integer> list: lists){
            queue.add(new SetterForCompare(list, list.size()-1));
        }
        
 
        while(!queue.isEmpty() && newsFeed.size()<10){
        	SetterForCompare top = queue.poll();
        	newsFeed.add(top.list.get(top.index));
            
            top.index--;
            
            if(top.index>=0)
                queue.add(top);
        }
 
        String msg = Messages.getString("MyApp.4")+userId+Messages.getString("MyApp.5")+ newsFeed; //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println(msg);
                
    }
	
    
    /** Follower unfollow a followee. */
    public void unfollow(int followerId, int followeeId) {
        if(followerId==followeeId)
            return ;
 
        HashSet<Integer> set = userList.get(followerId);
        if(set==null){
            return;
        }
 
        set.remove(followeeId);
        
        String msg = Messages.getString("MyApp.6")+followerId+Messages.getString("MyApp.7")+followeeId; //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println(msg);
    }
    
    
    
    public static void main(String[] args) {
		MyApp obj = new MyApp();
		
		// User 1 posts a new message (id = 5).
		obj.postMessage(1, 5);
		
		// User 1's news feed should return a list with 1 post id -> [5].
		obj.getNewsFeed(1);
		
		// User 1 follows user 2.
				obj.follow(1, 2);
		
		// User 2 posts a new message (id = 6).
		obj.postMessage (2, 6);
		
		// User 1's news feed should return a list with 2 messages ids -> [6, 5].
		// message id 6 should precede message id 5 because it is posted after id 5.
		obj.getNewsFeed(1);
		
		// User 1 unfollows user 2.
		obj.unfollow(1, 2);
		
		// User 1's news feed should return a list with 1 post id -> [5],
		// since user 1 is no longer following user 2.
		obj.getNewsFeed(1);
		
	}
    
    
    
}
