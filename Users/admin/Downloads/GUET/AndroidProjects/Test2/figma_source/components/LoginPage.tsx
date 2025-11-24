import { useState } from 'react';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Mail, Lock, Eye, EyeOff } from 'lucide-react';

interface LoginPageProps {
  onLogin: () => void;
}

export function LoginPage({ onLogin }: LoginPageProps) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onLogin();
  };

  return (
    <div className="min-h-screen flex flex-col bg-white px-6 pt-16 pb-8 max-w-md mx-auto">
      {/* Logo/标题区域 */}
      <div className="mb-12">
        <div className="w-16 h-16 bg-gradient-to-br from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center mb-4">
          <span className="text-white text-2xl">📱</span>
        </div>
        <h1 className="text-gray-900 mb-1">欢迎回来</h1>
        <p className="text-gray-500">登录您的账号</p>
      </div>

      {/* 登录表单 */}
      <form onSubmit={handleSubmit} className="flex-1 flex flex-col">
        {/* 邮箱输入 */}
        <div className="mb-4">
          <label className="block text-gray-700 mb-2">邮箱</label>
          <div className="relative">
            <Mail className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />
            <Input
              type="email"
              placeholder="请输入邮箱地址"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="pl-11 h-12 border-gray-200"
            />
          </div>
        </div>

        {/* 密码输入 */}
        <div className="mb-2">
          <label className="block text-gray-700 mb-2">密码</label>
          <div className="relative">
            <Lock className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />
            <Input
              type={showPassword ? 'text' : 'password'}
              placeholder="请输入密码"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="pl-11 pr-11 h-12 border-gray-200"
            />
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
              className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600"
            >
              {showPassword ? (
                <EyeOff className="w-5 h-5" />
              ) : (
                <Eye className="w-5 h-5" />
              )}
            </button>
          </div>
        </div>

        {/* 忘记密码 */}
        <div className="flex justify-end mb-6">
          <button type="button" className="text-blue-600 text-sm hover:text-blue-700">
            忘记密码？
          </button>
        </div>

        {/* 登录按钮 */}
        <Button
          type="submit"
          className="w-full h-12 bg-blue-600 hover:bg-blue-700 text-white rounded-lg mb-6"
        >
          登录
        </Button>

        {/* 分隔线 */}
        <div className="flex items-center gap-4 mb-6">
          <div className="flex-1 h-px bg-gray-200"></div>
          <span className="text-gray-400 text-sm">或者用其他方式登录</span>
          <div className="flex-1 h-px bg-gray-200"></div>
        </div>

        {/* 第三方登录 */}
        <div className="flex justify-center gap-6">
          <button
            type="button"
            className="w-14 h-14 rounded-full bg-green-500 hover:bg-green-600 flex items-center justify-center shadow-sm transition-colors"
            title="微信登录"
          >
            <svg className="w-7 h-7 text-white" viewBox="0 0 24 24" fill="currentColor">
              <path d="M8.5 5C4.9 5 2 7.5 2 10.6c0 1.8 1 3.4 2.6 4.4l-.6 1.8 2-.9c.7.2 1.4.3 2.1.3.3 0 .6 0 .9-.1-.2-.6-.3-1.2-.3-1.8 0-3.4 3.1-6.2 7-6.2.3 0 .6 0 .9.1C15.8 6.1 12.5 5 8.5 5zm-1.7 3.8c.5 0 .9.4.9.9s-.4.9-.9.9-.9-.4-.9-.9.4-.9.9-.9zm5 0c.5 0 .9.4.9.9s-.4.9-.9.9-.9-.4-.9-.9.4-.9.9-.9zM16 11.3c-3.3 0-6 2.3-6 5.2s2.7 5.2 6 5.2c.6 0 1.2-.1 1.8-.3l1.7.8-.5-1.5c1.4-.9 2.3-2.3 2.3-3.9 0-3-2.7-5.5-6-5.5zm-2.3 2.6c.4 0 .7.3.7.7s-.3.7-.7.7-.7-.3-.7-.7.3-.7.7-.7zm4.6 0c.4 0 .7.3.7.7s-.3.7-.7.7-.7-.3-.7-.7.3-.7.7-.7z"/>
            </svg>
          </button>
          <button
            type="button"
            className="w-14 h-14 rounded-full bg-black hover:bg-gray-800 flex items-center justify-center shadow-sm transition-colors"
            title="Apple登录"
          >
            <svg className="w-7 h-7 text-white" viewBox="0 0 24 24" fill="currentColor">
              <path d="M17.05 20.28c-.98.95-2.05.8-3.08.35-1.09-.46-2.09-.48-3.24 0-1.44.62-2.2.44-3.06-.35C2.79 15.25 3.51 7.59 9.05 7.31c1.35.07 2.29.74 3.08.8 1.18-.24 2.31-.93 3.57-.84 1.51.12 2.65.72 3.4 1.8-3.12 1.87-2.38 5.98.48 7.13-.57 1.5-1.31 2.99-2.54 4.09l.01-.01zM12.03 7.25c-.15-2.23 1.66-4.07 3.74-4.25.29 2.58-2.34 4.5-3.74 4.25z"/>
            </svg>
          </button>
        </div>

        {/* 注册提示 */}
        <div className="mt-auto pt-8 text-center">
          <span className="text-gray-500">还没有账号？</span>
          <button type="button" className="text-blue-600 ml-1 hover:text-blue-700">
            立即注册
          </button>
        </div>
      </form>
    </div>
  );
}
