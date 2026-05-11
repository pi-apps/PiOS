# Pi Open Source (PiOS)

FODUBU Unified Applications

 Project Title

FODUBU – Unified Pi-Powered Applications (PiOS)

Overview

FODUBU (Force de Développement et Ubuntu au Burundi) is a Pi Network–powered utility organization and multisector company building decentralized applications (DApps) that enable real economic activity through trade, agriculture, and environmental sustainability.

This Pi Open Source (PiOS) submission covers the FODUBU Unified Applications, a suite of three interoperable Pi-powered marketplaces that use Pi as the primary means of payment.

🌍 Mission & Vision

Mission
To power inclusive trade, agricultural growth, and environmental sustainability using Pi Network as a decentralized payment and utility layer.

Vision
To become a trusted Pi-powered digital infrastructure for trade and development across communities and markets.


## 🎯 The Three Applications

### 1. FODUBU - Main Unified App
- **URL**: https://trade.fodubu.com
- **Purpose**: Central marketplace and utility hub
- **Role**: Trade and service aggregation, entry point to ecosystem
- **Status**: each  app with App ID (across ecosystem)

### 2. TRACO - Agri-Connect Marketplace
- **URL**: https://traco.fodubu.com
- **Purpose**: Digital agricultural marketplace
- **Features**:
  - Buying/selling agricultural products
  - Seed distribution to branches and farmers
  - Agricultural value-chain connectivity
- **Status**: Has App ID from Pi App Studio

### 3. SETRA - Eco Marketplace
- **URL**: https://setra.fodubu.com (also https://setra2493.pinet.com)
- **Purpose**: Environment-focused sustainable trade
- **Features**:
  - All services documented in `SETRA-PROGRAMS-AND-SERVICES.md`
  - Reforestation, Aquaculture, Transportation, Clean Energy, Construction
- **Status**: has its ID From App Studio


## 🏗️ Technical Architecture

### Current Stack

#### Frontend
- ReactJS with Vite
- Pi JavaScript SDK (`@pi-apps/pi-sdk`)
- Pi Browser-compatible UI

#### Backend (fodubu-unified-api)
- **URL**: https://api.fodubu.com
- Node.js + Express.js
- Pi Backend SDK (`@pi-apps/backend`)
- Stellar SDK (`@stellar/stellar-sdk`)
- Secure Pi payment verification

#### Database
- MongoDB Atlas
- Environment-based configuration
- CI-ready structure

-

## 🔗 Repository Structure

### Main Repositories
1. **PiOS (This repo)**: https://github.com/FODUBU/PiOS
   - Documentation and project overview
   - Open source component

2. **fodubu-api**: https://github.com/FODUBU/fodubu-api
   - Unified backend for all 3 apps
   - Private repository (read-only access on request)

3. **Frontend Repos**
   - FODUBU Main frontend: https://github.com/FODUBU/fodubu
   - TRACO-MARKETPACE  v0: https://github.com/FODUBU/TRACO-MARKETPLACE
   - SETRA-MARKETPLACE v0: https://github.com/FODUBU/SETRA-MARKETPLACE


> Important Disclaimer
FODUBU is an independent community-built project.

Pi, Pi Network, and the Pi logo are trademarks of Pi Community Company.
This project is not affiliated with nor endorsed by Pi Network Core Team

Contribution guidelines and code of conduct are provided in the repository.

📜 License

This project is released under the MIT License, allowing open use, modification, and distribution while protecting contributors.

Summary

FODUBU PiOS represents a real-world Pi utility ecosystem combining:

Trade

Agriculture

Environmental sustainability


All powered by Pi Network, built with modern web technologies, and designed for scalable, secure, and inclusive commerce.

© FODUBU — Force de Développement et Ubuntu au Burundi
