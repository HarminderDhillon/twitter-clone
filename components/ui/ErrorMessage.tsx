import React from 'react';

interface ErrorMessageProps {
  message: string;
  type?: 'warning' | 'error' | 'info';
}

export function ErrorMessage({ 
  message, 
  type = 'error' 
}: ErrorMessageProps) {
  const styles = {
    error: 'bg-red-50 border-red-200 text-red-800',
    warning: 'bg-yellow-50 border-yellow-200 text-yellow-800',
    info: 'bg-blue-50 border-blue-200 text-blue-800',
  };

  return (
    <div className={`${styles[type]} px-4 py-3 rounded border mb-4`}>
      <p>{message}</p>
    </div>
  );
} 