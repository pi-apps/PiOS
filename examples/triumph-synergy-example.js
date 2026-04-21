/**
 * Triumph Synergy Integration Examples
 * 
 * Import Chainlink integration utilities
 */
import { getChainlinkPrice, requestChainlinkVRF, getVRFRandomness } from '@triumph-synergy/core';

async function trackPortfolio(piAccount) {
  try {
    // Validate input
    if (!piAccount || typeof piAccount !== 'string') {
      throw new Error('Invalid piAccount format');
    }

    const portfolioResponse = await fetch(https://api.triumph-synergy.com/portfolio/$\{piAccount\});
    const portfolio = await portfolioResponse.json();

    const pricesResponse = await fetch(https://api.triumph-synergy.com/api/chainlink/prices);
    const prices = await pricesResponse.json();

    // Validate response structure
    if (!portfolio || !Array.isArray(portfolio.assets) || !Array.isArray(prices)) {
      throw new Error('Invalid portfolio or price data received from API');
    }

    let totalValue = 0;
    const holdings = portfolio.assets
      .map(asset => {
        const price = prices.find(p => p.pair === $\{asset.symbol\}/USD);
        // Skip assets with missing or invalid price data
        if (!price || typeof price.rate !== 'number') {
          console.warn(Price not found for $\{asset.symbol\});
          return null;
        }
        const value = asset.amount * price.rate;
        totalValue += value;
        return { symbol: asset.symbol, amount: asset.amount, price: price.rate, value };
      })
      .filter(Boolean);

    console.log(Portfolio Value: \C:\Users\13865\PiOS\examples\triumph-synergy-example.js\{totalValue.toFixed(2)\});
    return { totalValue, holdings };
  } catch (error) {
    console.error('Error tracking portfolio:', error);
    return { totalValue: 0, holdings: [] };
  }
}

async function executeSmartTrade(config) {
  try {
    const { fromAsset, toAsset, targetPrice, amount } = config;

    // Validate inputs for financial operations
    if (typeof amount !== 'number' || amount <= 0) {
      throw new Error('Amount must be a positive number');
    }
    if (typeof targetPrice !== 'number' || targetPrice <= 0) {
      throw new Error('Target price must be a positive number');
    }
    if (!/^[A-Z]{2,}$/.test(fromAsset) || !/^[A-Z]{2,}$/.test(toAsset)) {
      throw new Error('Asset symbols must be valid format (e.g., BTC, ETH)');
    }

    const prices = await fetch(https://api.triumph-synergy.com/api/chainlink/prices, {
      headers: { 'Content-Type': 'application/json' }
    }).then(r => r.json());

    const toPrice = prices.find(p => p.pair === $\{toAsset\}/USD);

    // Validate price data exists before accessing
    if (!toPrice || toPrice.rate == null) {
      console.log('Target price condition not met or price not available');
      return null;
    }

    if (toPrice.rate <= targetPrice) {
      const tradeResult = await fetch(https://api.triumph-synergy.com/trade, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ 
          from: fromAsset, 
          to: toAsset, 
          amount, 
          executionPrice: toPrice.rate 
        })
      }).then(r => r.json());
      return tradeResult;
    }
    return null;
  } catch (error) {
    console.error('Failed to execute smart trade:', error);
    return null;
  }
}

async function stakeWithAutomation(piAccount, stakingAmount) {
  try {
    // Validate inputs for sensitive financial operations
    if (!piAccount || typeof piAccount !== 'string') {
      throw new Error('Invalid piAccount format');
    }
    if (typeof stakingAmount !== 'number' || stakingAmount <= 0) {
      throw new Error('Staking amount must be a positive number');
    }

    const stakingResult = await fetch(https://api.triumph-synergy.com/staking/stake, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ 
        account: piAccount, 
        amount: stakingAmount, 
        autoCompound: true 
      })
    }).then(r => r.json());

    // Register Keeper automation for staking rebalance
    const automation = await fetch(https://api.triumph-synergy.com/api/chainlink/automations, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ 
        stakingId: stakingResult.stakingId, 
        action: 'rebalance', 
        interval: 86400 
      })
    }).then(r => r.json());

    return { stakingResult, automation };
  } catch (error) {
    console.error('Error with staking automation:', error);
    return null;
  }
}

async function processPayment(config) {
  try {
    const { fromChain, toChain, amount, recipientAddress } = config;

    // Validate inputs for payment processing
    if (typeof amount !== 'number' || amount <= 0) {
      throw new Error('Amount must be a positive number');
    }
    if (!recipientAddress || !/^0x[a-fA-F0-9]{40}$/.test(recipientAddress)) {
      throw new Error('Invalid recipient address format');
    }
    if (!fromChain || !toChain) {
      throw new Error('Valid chain identifiers required');
    }

    const rates = await fetch(https://api.triumph-synergy.com/api/chainlink/prices, {
      headers: { 'Content-Type': 'application/json' }
    }).then(r => r.json());

    const payment = await fetch(https://api.triumph-synergy.com/payment/cross-chain, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ 
        fromChain, 
        toChain, 
        amount, 
        recipientAddress, 
        exchangeRates: rates 
      })
    }).then(r => r.json());

    return payment;
  } catch (error) {
    console.error('Error processing payment:', error);
    return null;
  }
}

async function monitorOracleHealth() {
  try {
    const health = await fetch(https://api.triumph-synergy.com/api/chainlink/health, {
      headers: { 'Content-Type': 'application/json' }
    }).then(r => r.json());

    console.log(Oracle Status: $\{health.status\});
    console.log(Network Health: $\{health.networkStatus\});
    return health;
  } catch (error) {
    console.error('Error monitoring oracle health:', error);
    return null;
  }
}

module.exports = { 
  trackPortfolio, 
  executeSmartTrade, 
  stakeWithAutomation, 
  processPayment, 
  monitorOracleHealth 
};
