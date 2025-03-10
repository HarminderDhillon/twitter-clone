'use client';

import React, { useEffect, useState } from 'react';
import { useParams } from 'next/navigation';
import { fetchUserByUsername } from '@/app/api/users';
import { fetchPosts } from '@/app/api/posts';
import { Post, User } from '@/types';
import { PostCard } from '@/components/posts/PostCard';
import { LoadingSpinner } from '@/components/ui/LoadingSpinner';
import { ErrorMessage } from '@/components/ui/ErrorMessage';

export default function UserProfile() {
  const params = useParams();
  const username = params.username as string;
  
  const [user, setUser] = useState<User | null>(null);
  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    async function loadUserAndPosts() {
      try {
        setLoading(true);
        
        // Fetch user data
        const userData = await fetchUserByUsername(username);
        setUser(userData);
        
        if (userData) {
          // Fetch user posts
          const postsData = await fetchPosts();
          // Filter posts by user
          const userPosts = postsData.filter(post => post.user.username === username);
          setPosts(userPosts);
        }
        
        setLoading(false);
      } catch (err) {
        console.error('Error loading user profile:', err);
        setError('Error loading user profile. Please try again later.');
        setLoading(false);
      }
    }

    if (username) {
      loadUserAndPosts();
    }
  }, [username]);

  if (loading) {
    return (
      <div className="max-w-2xl mx-auto px-4 py-16">
        <LoadingSpinner message="Loading profile..." />
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-2xl mx-auto px-4 py-8">
        <ErrorMessage message={error} />
      </div>
    );
  }
  
  if (!user) {
    return (
      <div className="max-w-2xl mx-auto px-4 py-8">
        <ErrorMessage 
          message={`The user @${username} does not exist.`} 
          type="warning" 
        />
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto px-4 py-8">
      {/* Profile Header */}
      <header className="mb-8">
        <div className="h-32 bg-blue-100 rounded-t-lg mb-16 relative">
          {user.headerImage && (
            <img 
              src={user.headerImage} 
              alt={`${user.displayName}'s header`} 
              className="w-full h-full object-cover rounded-t-lg"
            />
          )}
          
          {/* Profile Image */}
          <div className="absolute -bottom-12 left-4">
            <div className="rounded-full h-24 w-24 border-4 border-white bg-white overflow-hidden">
              {user.profileImage ? (
                <img 
                  src={user.profileImage} 
                  alt={`${user.displayName}'s profile`}
                  className="w-full h-full object-cover" 
                />
              ) : (
                <div className="bg-blue-500 w-full h-full flex items-center justify-center text-white text-2xl font-bold">
                  {user.displayName.charAt(0)}
                </div>
              )}
            </div>
          </div>
        </div>
        
        <div className="flex justify-end">
          <button className="bg-blue-500 text-white px-4 py-2 rounded-full hover:bg-blue-600 transition">
            Follow
          </button>
        </div>
        
        <div className="mt-4">
          <h1 className="text-2xl font-bold flex items-center">
            {user.displayName}
            {user.verified && (
              <svg className="ml-1 h-5 w-5 text-blue-500" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M6.267 3.455a3.066 3.066 0 001.745-.723 3.066 3.066 0 013.976 0 3.066 3.066 0 001.745.723 3.066 3.066 0 012.812 2.812c.051.643.304 1.254.723 1.745a3.066 3.066 0 010 3.976 3.066 3.066 0 00-.723 1.745 3.066 3.066 0 01-2.812 2.812 3.066 3.066 0 00-1.745.723 3.066 3.066 0 01-3.976 0 3.066 3.066 0 00-1.745-.723 3.066 3.066 0 01-2.812-2.812 3.066 3.066 0 00-.723-1.745 3.066 3.066 0 010-3.976 3.066 3.066 0 00.723-1.745 3.066 3.066 0 012.812-2.812zm7.44 5.252a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
              </svg>
            )}
          </h1>
          <p className="text-gray-600">@{user.username}</p>
          {user.bio && <p className="mt-2">{user.bio}</p>}
          
          <div className="flex mt-4 space-x-4 text-sm">
            <div>
              <span className="font-bold">{user.followingCount}</span> Following
            </div>
            <div>
              <span className="font-bold">{user.followersCount}</span> Followers
            </div>
            <div>
              Joined {new Date(user.createdAt).toLocaleDateString()}
            </div>
          </div>
        </div>
      </header>
      
      {/* User Posts */}
      <section>
        <h2 className="text-xl font-semibold mb-4">Posts</h2>
        
        {posts.length === 0 ? (
          <div className="text-center py-10 text-gray-500">
            <p>No posts yet.</p>
          </div>
        ) : (
          <div className="space-y-4">
            {posts.map((post) => (
              <PostCard key={post.id} post={post} />
            ))}
          </div>
        )}
      </section>
    </div>
  );
} 