import { PrismaClient } from '@prisma/client'
import bcrypt from 'bcryptjs'

const prisma = new PrismaClient()

async function main() {
  await prisma.category.deleteMany()
  await prisma.product.deleteMany()
  await prisma.user.deleteMany()

  const categories = ['Toys', 'Grocery', 'Vegetables', 'Animal Products', 'Fashion']
  const createdCats = [] as any
  for (const name of categories) {
    const c = await prisma.category.create({ data: { name, slug: name.toLowerCase().replace(/\s+/g,'-') } })
    createdCats.push(c)
  }

  const password = await bcrypt.hash('changeme', 10)

  // admin
  await prisma.user.create({ data: { name: 'Admin', email: 'admin@zimtah.local', password, role: 'ADMIN', termsAccepted: true } })

  // sample sellers
  for (let i = 1; i <= 5; i++) {
    const seller = await prisma.user.create({
      data: {
        name: `Seller ${i}`,
        email: `seller${i}@zimtah.local`,
        password,
        role: 'SELLER',
        termsAccepted: true,
        mpesaPaybill: `123456${i}`,
        paypalEmail: `seller${i}@paypal.example`,
        phone: `+100000000${i}`,
        locationLat: -1.28 + i * 0.01,
        locationLng: 36.8 + i * 0.01,
      }
    })

    // create products
    for (let p = 1; p <= 3; p++) {
      await prisma.product.create({
        data: {
          title: `Product ${p} from Seller ${i}`,
          description: `Sample product ${p} for seller ${i}`,
          price: 10 * p,
          image: '',
          sellerId: seller.id,
          categoryId: createdCats[(p - 1) % createdCats.length].id
        }
      })
    }
  }

  console.log('Seed finished.')
}

main()
  .catch(e => {
    console.error(e)
    process.exit(1)
  })
  .finally(async () => {
    await prisma.$disconnect()
  })
