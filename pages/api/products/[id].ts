import { NextApiRequest, NextApiResponse } from 'next'
import prisma from '../../../src/lib/prisma'

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const {
    query: { id },
    method,
  } = req

  if (method === 'GET') {
    const product = await prisma.product.findUnique({ where: { id: Number(id) }, include: { seller: true, category: true } })
    if (!product) return res.status(404).json({ error: 'Not found' })
    return res.json(product)
  }

  res.setHeader('Allow', ['GET'])
  res.status(405).end(`Method ${method} Not Allowed`)
}
