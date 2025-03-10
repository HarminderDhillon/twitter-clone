'use client';

import { useEffect, useState } from 'react';
import { fetchPosts } from '@/app/api/posts';
import { Post } from '@/types';
import { PostCard } from '@/components/posts/PostCard';

export default function Home() {
  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    async function loadPosts() {
      try {
        const data = await fetchPosts();
        setPosts(data);
        setLoading(false);
      } catch (err) {
        setError("Error loading posts. Make sure your backend server is running at http://localhost:8080/api");
        setLoading(false);
        console.error(err);
      }
    }

    loadPosts();
  }, []);

  return (
    <div className="max-w-2xl mx-auto px-4 py-8">
      <header className="mb-8">
        <h1 className="text-3xl font-bold">Twitter Clone</h1>
        <p className="text-gray-600 mt-2">
          A simple Twitter clone UI connected to Spring Boot backend
        </p>
      </header>

      <section>
        <h2 className="text-xl font-semibold mb-4">Recent Posts</h2>
        
        {loading && (
          <div className="text-center py-10">
            <div className="inline-block animate-spin rounded-full h-8 w-8 border-4 border-t-blue-500 border-r-transparent border-b-blue-500 border-l-transparent"></div>
            <p className="mt-2 text-gray-600">Loading posts...</p>
          </div>
        )}
        
        {error && (
          <div className="bg-red-50 border border-red-200 text-red-800 px-4 py-3 rounded mb-4">
            <p>{error}</p>
          </div>
        )}
        
        {!loading && !error && posts.length === 0 && (
          <div className="text-center py-10 text-gray-500">
            <p>No posts found. Create your first post!</p>
          </div>
        )}
        
        {posts.map((post) => (
          <PostCard key={post.id} post={post} />
        ))}
      </section>
    </div>
  );
} 