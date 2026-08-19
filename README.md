# Zimtah - Minimal MVP

This repository contains a minimal MVP scaffold for Zimtah — a marketplace/marketing platform.

What's included in this branch (feature/scaffold):

- Next.js + TypeScript + Tailwind skeleton
- NextAuth (email/password credentials) scaffold
- Prisma schema (SQLite dev) with seed script (5 sellers, 15 products)
- Product CRUD API routes and simple frontend listing
- Local image upload (dev) stubs
- Dark mode toggle placeholder and splash placeholder

Getting started (local):

1) Install dependencies
   npm install

2) Copy env example and adjust
   cp .env.example .env
   (set NEXTAUTH_SECRET and other keys)

3) Run migrations and seed
   npx prisma generate
   npx prisma migrate dev --name init
   npm run prisma:seed

4) Run dev
   npm run dev

Dev admin (seeded):
- email: admin@zimtah.local
- password: changeme

Notes:
- This is a minimal MVP. Production integrations (S3, Stripe, MPesa, TURN servers) are stubbed and require keys.
- The deal-closure legal PDF and audio recording are scaffolded; consult legal counsel for court validity.
