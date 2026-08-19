import { NextApiRequest, NextApiResponse } from 'next'
import prisma from '../../../src/lib/prisma'

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  if (req.method === 'GET') {
    const q = (req.query.q as string) || undefined
    const where = q
      ? { OR: [{ title: { contains: q, mode: 'insensitive' } }, { description: { contains: q, mode: 'insensitive' } }] }
      : {}
    const products = await prisma.product.findMany({ where, include: { seller: true, category: true }, take: 50 })
    res.json(products)
    return
  }

  if (req.method === 'POST') {
    const { title, description, price, sellerId, categoryId } = req.body
    const product = await prisma.product.create({ data: { title, description, price: Number(price || 0), sellerId: Number(sellerId), categoryId: categoryId ? Number(categoryId) : undefined } })
    res.status(201).json(product)
    return
  }

  res.setHeader('Allow', ['GET', 'POST'])
  res.status(405).end(`Method ${req.method} Not Allowed`)
}
