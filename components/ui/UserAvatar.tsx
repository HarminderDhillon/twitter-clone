import React from 'react';

interface UserAvatarProps {
  username: string;
  image?: string | null;
  size?: 'sm' | 'md' | 'lg';
  showVerified?: boolean;
  isVerified?: boolean;
}

export function UserAvatar({
  username,
  image,
  size = 'md',
  showVerified = false,
  isVerified = false
}: UserAvatarProps) {
  const sizeClasses = {
    sm: 'h-8 w-8',
    md: 'h-10 w-10',
    lg: 'h-16 w-16'
  };

  const getInitials = (username: string) => {
    return username.charAt(0).toUpperCase();
  };

  return (
    <div className="relative">
      <div className={`${sizeClasses[size]} rounded-full overflow-hidden bg-gray-200 flex items-center justify-center text-gray-800 font-semibold`}>
        {image ? (
          <img src={image} alt={`${username}'s avatar`} className="w-full h-full object-cover" />
        ) : (
          <span>{getInitials(username)}</span>
        )}
      </div>
      
      {showVerified && isVerified && (
        <div className="absolute bottom-0 right-0 bg-blue-500 rounded-full p-1">
          <svg className="h-3 w-3 text-white" fill="currentColor" viewBox="0 0 20 20">
            <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd" />
          </svg>
        </div>
      )}
    </div>
  );
} 