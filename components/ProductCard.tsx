import Link from 'next/link'

export default function ProductCard({ product }: { product: any }) {
  return (
    <div className="p-4 border rounded bg-white dark:bg-gray-800">
      <h2 className="font-semibold">{product.title}</h2>
      <p className="text-sm">{product.description}</p>
      <p className="mt-2 font-bold">${product.price}</p>
      <p className="text-xs mt-1">Seller: {product.seller?.name}</p>
      <div className="mt-3">
        <Link href={`/products/${product.id}`} className="text-blue-600">View</Link>
      </div>
    </div>
  )
}
