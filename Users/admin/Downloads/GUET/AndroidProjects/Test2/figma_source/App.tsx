import { useState } from 'react';
import { LoginPage } from './components/LoginPage';
import { ProfilePage } from './components/ProfilePage';

export default function App() {
  const [currentPage, setCurrentPage] = useState<'login' | 'profile'>('login');
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogin = () => {
    setIsLoggedIn(true);
    setCurrentPage('profile');
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setCurrentPage('login');
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* 页面切换按钮 - 仅用于演示 */}
      <div className="fixed top-4 right-4 z-50 flex gap-2">
        <button
          onClick={() => setCurrentPage('login')}
          className={`px-3 py-1 rounded-full text-sm ${
            currentPage === 'login'
              ? 'bg-blue-600 text-white'
              : 'bg-white text-gray-600 shadow'
          }`}
        >
          登录
        </button>
        <button
          onClick={() => setCurrentPage('profile')}
          className={`px-3 py-1 rounded-full text-sm ${
            currentPage === 'profile'
              ? 'bg-blue-600 text-white'
              : 'bg-white text-gray-600 shadow'
          }`}
        >
          个人中心
        </button>
      </div>

      {/* 页面内容 */}
      {currentPage === 'login' ? (
        <LoginPage onLogin={handleLogin} />
      ) : (
        <ProfilePage onLogout={handleLogout} />
      )}
    </div>
  );
}
