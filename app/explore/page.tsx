'use client';

import React, { useEffect, useState } from 'react';
import { fetchPosts } from '@/app/api/posts';
import { fetchUsers } from '@/app/api/users';
import { Post, User } from '@/types';
import { PostCard } from '@/components/posts/PostCard';
import { LoadingSpinner } from '@/components/ui/LoadingSpinner';
import { ErrorMessage } from '@/components/ui/ErrorMessage';
import { UserAvatar } from '@/components/ui/UserAvatar';
import Link from 'next/link';

export default function Explore() {
  const [posts, setPosts] = useState<Post[]>([]);
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [activeTab, setActiveTab] = useState<'posts' | 'users'>('posts');

  useEffect(() => {
    async function loadData() {
      try {
        setLoading(true);
        
        const [postsData, usersData] = await Promise.all([
          fetchPosts(),
          fetchUsers()
        ]);
        
        setPosts(postsData);
        setUsers(usersData);
        setLoading(false);
      } catch (err) {
        console.error('Error loading explore data:', err);
        setError('Error loading data. Please try again later.');
        setLoading(false);
      }
    }

    loadData();
  }, []);

  if (loading) {
    return (
      <div className="max-w-2xl mx-auto px-4 py-16">
        <LoadingSpinner message="Loading explore content..." />
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

  return (
    <div className="max-w-2xl mx-auto px-4 py-8">
      <header className="mb-8">
        <h1 className="text-3xl font-bold">Explore</h1>
        <p className="text-gray-600 mt-2">
          Discover posts and users
        </p>
      </header>

      {/* Tabs */}
      <div className="flex border-b mb-6">
        <button
          className={`px-4 py-2 font-medium ${
            activeTab === 'posts'
              ? 'text-blue-500 border-b-2 border-blue-500'
              : 'text-gray-500 hover:text-gray-700'
          }`}
          onClick={() => setActiveTab('posts')}
        >
          Posts
        </button>
        <button
          className={`px-4 py-2 font-medium ${
            activeTab === 'users'
              ? 'text-blue-500 border-b-2 border-blue-500'
              : 'text-gray-500 hover:text-gray-700'
          }`}
          onClick={() => setActiveTab('users')}
        >
          Users
        </button>
      </div>

      {/* Content */}
      {activeTab === 'posts' && (
        <section>
          {posts.length === 0 ? (
            <div className="text-center py-10 text-gray-500">
              <p>No posts found.</p>
            </div>
          ) : (
            <div className="space-y-4">
              {posts.map((post) => (
                <PostCard key={post.id} post={post} />
              ))}
            </div>
          )}
        </section>
      )}

      {activeTab === 'users' && (
        <section>
          {users.length === 0 ? (
            <div className="text-center py-10 text-gray-500">
              <p>No users found.</p>
            </div>
          ) : (
            <div className="space-y-4">
              {users.map((user) => (
                <Link 
                  href={`/users/${user.username}`}
                  key={user.id}
                  className="flex items-center p-4 rounded-lg border border-gray-200 hover:bg-gray-50 transition"
                >
                  <UserAvatar 
                    username={user.username}
                    image={user.profileImage}
                    size="md"
                    showVerified={true}
                    isVerified={user.verified}
                  />
                  <div className="ml-3">
                    <p className="font-medium text-gray-900">{user.displayName}</p>
                    <p className="text-gray-500">@{user.username}</p>
                  </div>
                  <div className="ml-auto text-sm text-gray-500">
                    <span className="font-semibold">{user.followersCount}</span> followers
                  </div>
                </Link>
              ))}
            </div>
          )}
        </section>
      )}
    </div>
  );
} 