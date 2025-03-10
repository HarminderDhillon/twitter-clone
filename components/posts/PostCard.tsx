import { Post } from '@/types';
import { formatDate, formatNumber } from '@/lib/utils';

interface PostCardProps {
  post: Post;
}

export function PostCard({ post }: PostCardProps) {
  return (
    <div className="border border-gray-200 p-4 mb-4 rounded-lg">
      <div className="flex items-start space-x-3">
        <div className="h-10 w-10 rounded-full overflow-hidden">
          <img 
            src={post.user.profileImage || 'https://via.placeholder.com/40'} 
            alt={post.user.displayName}
            className="h-full w-full object-cover"
          />
        </div>
        <div className="flex-1">
          <div className="flex items-center">
            <span className="font-bold">{post.user.displayName}</span>
            {post.user.verified && (
              <span className="ml-1 text-blue-500">✓</span>
            )}
            <span className="ml-2 text-gray-500">@{post.user.username}</span>
            <span className="mx-1 text-gray-500">·</span>
            <span className="text-gray-500">{formatDate(post.createdAt)}</span>
          </div>
          
          <p className="mt-2 text-gray-900">{post.content}</p>
          
          <div className="mt-3 flex justify-between text-gray-500">
            <div className="flex items-center space-x-1">
              <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
              </svg>
              <span>{formatNumber(post.repliesCount)}</span>
            </div>
            <div className="flex items-center space-x-1 text-green-600">
              <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
              </svg>
              <span>{formatNumber(post.repostsCount)}</span>
            </div>
            <div className="flex items-center space-x-1 text-red-600">
              <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
              </svg>
              <span>{formatNumber(post.likesCount)}</span>
            </div>
            <div className="flex items-center space-x-1">
              <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.368 2.684 3 3 0 00-5.368-2.684z" />
              </svg>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
} 