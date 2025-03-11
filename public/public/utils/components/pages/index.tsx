import { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import { getData, setData } from '../utils/db';

export default function Home() {
  const [username, setUsername] = useState('');

  useEffect(() => {
    async function fetchUser() {
      const storedName = await getData('username');
      if (storedName) setUsername(storedName);
    }
    fetchUser();
  }, []);

  const handleSave = async () => {
    await setData('username', username);
  };

  return (
    <div className="min-h-screen bg-white dark:bg-gray-900 text-gray-900 dark:text-white">
      <Navbar />
      <div className="p-4">
        <h2 className="text-2xl font-bold">Selamat datang di PiTradeHub! 🚀</h2>
        <p className="mt-2">Masukkan nama Anda:</p>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="p-2 border rounded w-full mt-2"
        />
        <button onClick={handleSave} className="mt-4 bg-blue-600 text-white px-4 py-2 rounded">
          Simpan
        </button>
      </div>
    </div>
  );
}
