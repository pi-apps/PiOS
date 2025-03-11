import { openDB } from 'idb';

export async function setData(key: string, value: any) {
  const db = await openDB('PiTradeHubDB', 1, {
    upgrade(db) {
      db.createObjectStore('store');
    },
  });
  await db.put('store', value, key);
}

export async function getData(key: string) {
  const db = await openDB('PiTradeHubDB', 1);
  return db.get('store', key);
}
