import React from 'react';

interface LoadingSpinnerProps {
  size?: 'small' | 'medium' | 'large';
  message?: string;
}

export function LoadingSpinner({ 
  size = 'medium', 
  message = 'Loading...'
}: LoadingSpinnerProps) {
  const sizeClasses = {
    small: 'h-4 w-4 border-2',
    medium: 'h-8 w-8 border-4',
    large: 'h-12 w-12 border-6',
  };

  return (
    <div className="flex flex-col items-center justify-center py-4">
      <div 
        className={`
          animate-spin rounded-full 
          border-t-blue-500 border-r-transparent border-b-blue-500 border-l-transparent
          ${sizeClasses[size]}
        `}
      />
      {message && (
        <p className="mt-2 text-gray-600 text-sm">{message}</p>
      )}
    </div>
  );
} 