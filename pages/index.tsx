import Link from 'next/link'
import useSWR from 'swr'
import ProductCard from '../../components/ProductCard'
import { useState } from 'react'

const fetcher = (url: string) => fetch(url).then(res => res.json())

export default function Home() {
  const [q, setQ] = useState('')
  const { data: products } = useSWR(() => `/api/products${q ? `?q=${encodeURIComponent(q)}` : ''}`, fetcher)

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900 text-gray-900 dark:text-gray-100">
      <header className="p-6 border-b bg-white dark:bg-black">
        <div className="max-w-4xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">Zimtah</h1>
          <div className="flex gap-4">
            <Link href="/dashboard/seller">Seller Dashboard</Link>
            <a href="/api/auth/signin">Sign in</a>
          </div>
        </div>
      </header>
      <main className="max-w-4xl mx-auto p-6">
        <div className="mb-6">
          <input value={q} onChange={(e) => setQ(e.target.value)} placeholder="Search products or sellers" className="w-full border rounded p-2" />
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {products ? products.map((p: any) => <ProductCard key={p.id} product={p} />) : <p>Loading...</p>}
        </div>
      </main>
    </div>
  )
}
