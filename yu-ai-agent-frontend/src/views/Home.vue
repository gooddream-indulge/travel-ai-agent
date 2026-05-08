<template>
  <div class="home-container">
    <div class="hero-scenery" aria-hidden="true">
      <div class="sun"></div>
      <div class="mountain mountain-back"></div>
      <div class="mountain mountain-front"></div>
      <div class="lake"></div>
    </div>
    <div class="header">
      <div class="brand-pill">GoodDream Travel AI</div>
      <div class="title-wrapper">
        <h1 class="main-title">GoodDream的AI旅小智</h1>
      </div>
      <p class="subtitle">/ 探索AI的无限可能 /</p>
    </div>
    
    <div class="apps-container">
      <div class="app-card" @click="navigateTo('/travel-master')">
        <div class="card-media travel-media">
          <span>🧭</span>
        </div>
        <div class="app-info">
          <div class="app-title">AI旅小智</div>
          <div class="app-desc">智能旅行助手，为您提供旅游建议</div>
        </div>
        <div class="app-button">
          <span class="btn-text">立即体验</span>
          <span class="btn-icon">→</span>
        </div>
      </div>
      
      <div class="app-card" @click="navigateTo('/super-agent')">
        <div class="card-media planner-media">
          <span>🗺️</span>
        </div>
        <div class="app-info">
          <div class="app-title">AI超级旅小智</div>
          <div class="app-desc">全能AI智能体，帮您规划完美旅程</div>
        </div>
        <div class="app-button">
          <span class="btn-text">立即体验</span>
          <span class="btn-icon">→</span>
        </div>
      </div>
    </div>
    
    <div class="travel-notes" aria-hidden="true">
      <span>Departure</span>
      <span>Route</span>
      <span>Memory</span>
    </div>
    
    <AppFooter />
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useHead } from '@vueuse/head'
import AppFooter from '../components/AppFooter.vue'

// 设置页面标题和元数据
useHead({
  title: 'Gooddream的AI超级智能体应用平台 - 首页',
  meta: [
    {
      name: 'description',
      content: 'Gooddream的AI超级智能体应用平台提供AI旅小智和AI超级旅小智服务，满足您的各种AI对话需求'
    },
    {
      name: 'keywords',
      content: 'AI智能体,AI应用,AI旅小智,AI助手,智能对话,GoodDream,AI超级旅小智,首页'
    }
  ]
})

const router = useRouter()

const navigateTo = (path) => {
  router.push(path)
}
</script>

<style scoped>
.home-container {
  --travel-teal: #0f766e;
  --travel-mint: #14b8a6;
  --travel-sand: #fff7ed;
  --travel-orange: #f97316;
  --travel-ink: #19333b;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background:
    linear-gradient(180deg, rgba(231, 250, 245, 0.96) 0%, rgba(255, 247, 237, 0.9) 52%, #ffffff 100%);
  position: relative;
  overflow: hidden;
  color: var(--travel-ink);
}

.hero-scenery {
  position: absolute;
  inset: 0 0 auto 0;
  height: 420px;
  background:
    linear-gradient(180deg, #bfeee7 0%, #e7faf5 64%, rgba(255, 247, 237, 0) 100%);
  overflow: hidden;
  z-index: 0;
}

.sun {
  position: absolute;
  width: 110px;
  height: 110px;
  top: 74px;
  right: 18%;
  border-radius: 50%;
  background: radial-gradient(circle, #fff7ad 0%, #fbbf24 72%);
  box-shadow: 0 0 60px rgba(251, 191, 36, 0.45);
}

.mountain {
  position: absolute;
  bottom: 68px;
  width: 54vw;
  height: 210px;
  clip-path: polygon(0 100%, 28% 38%, 45% 70%, 62% 22%, 100% 100%);
}

.mountain-back {
  left: -5vw;
  background: linear-gradient(135deg, #7dd3c7, #4f9f98);
  opacity: 0.62;
}

.mountain-front {
  right: -8vw;
  bottom: 50px;
  height: 250px;
  background: linear-gradient(135deg, #0f766e, #63b7a8);
  opacity: 0.78;
}

.lake {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  height: 120px;
  background:
    repeating-linear-gradient(0deg, rgba(255,255,255,0.26) 0 2px, transparent 2px 18px),
    linear-gradient(180deg, rgba(20, 184, 166, 0.28), rgba(255, 247, 237, 0));
}

.header {
  padding: 76px 20px 34px;
  text-align: center;
  background-color: transparent;
  position: relative;
  z-index: 2;
}

.brand-pill {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 8px 16px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(15, 118, 110, 0.16);
  color: var(--travel-teal);
  font-size: 0.86rem;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  box-shadow: 0 12px 30px rgba(15, 118, 110, 0.1);
}

.title-wrapper {
  position: relative;
  display: inline-block;
  margin: 22px 0 16px;
}

.main-title {
  font-size: clamp(2.4rem, 6vw, 4.8rem);
  font-weight: 700;
  color: #12343b;
  text-shadow: 0 8px 28px rgba(255, 255, 255, 0.76);
  letter-spacing: 0;
  position: relative;
}

.subtitle {
  font-size: 1.2rem;
  color: #42656a;
  max-width: 600px;
  margin: 0 auto 20px;
  letter-spacing: 0.08em;
}

.apps-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 28px;
  max-width: 1200px;
  margin: 34px auto 72px;
  padding: 0 20px;
  flex: 1;
  position: relative;
  z-index: 2;
}

.app-card {
  width: 360px;
  background: rgba(255, 255, 255, 0.82);
  backdrop-filter: blur(16px);
  border-radius: 24px;
  box-shadow: 0 22px 55px rgba(15, 82, 86, 0.16);
  border: 1px solid rgba(15, 118, 110, 0.12);
  padding: 18px 18px 24px;
  cursor: pointer;
  transition: transform 0.25s ease, box-shadow 0.25s ease, border-color 0.25s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.app-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 28px 70px rgba(15, 82, 86, 0.2);
  border-color: rgba(15, 118, 110, 0.22);
}

.card-media {
  width: 100%;
  height: 174px;
  border-radius: 18px;
  margin-bottom: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 4.2rem;
  overflow: hidden;
}

.travel-media {
  background:
    radial-gradient(circle at 18% 20%, #fef3c7 0 10%, transparent 11%),
    linear-gradient(160deg, rgba(20, 184, 166, 0.18), rgba(15, 118, 110, 0.72)),
    linear-gradient(22deg, transparent 0 44%, rgba(255, 255, 255, 0.45) 45% 50%, transparent 51%),
    #a7f3d0;
}

.planner-media {
  background:
    linear-gradient(135deg, rgba(249, 115, 22, 0.18), rgba(20, 184, 166, 0.15)),
    repeating-linear-gradient(30deg, rgba(15, 118, 110, 0.16) 0 2px, transparent 2px 28px),
    #fff7ed;
}

.app-info {
  text-align: center;
  margin-bottom: 30px;
  width: 100%;
}

.app-title {
  font-size: 1.6rem;
  font-weight: bold;
  color: #12343b;
  margin-bottom: 12px;
}

.app-desc {
  font-size: 1rem;
  color: #5f7375;
  line-height: 1.6;
}

.app-button {
  background: linear-gradient(135deg, var(--travel-teal), var(--travel-mint));
  color: white;
  padding: 12px 24px;
  border-radius: 999px;
  font-weight: 500;
  transition: all 0.3s;
  margin-top: auto;
  display: flex;
  align-items: center;
  position: relative;
  overflow: hidden;
  border: 1px solid rgba(255, 255, 255, 0.24);
  box-shadow: 0 10px 24px rgba(15, 118, 110, 0.2);
}

.app-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.7s;
}

.app-button:hover {
  box-shadow: 0 14px 28px rgba(15, 118, 110, 0.25);
  transform: scale(1.05);
}

.app-button:hover::before {
  left: 100%;
}

.btn-text {
  margin-right: 8px;
  letter-spacing: 1px;
}

.btn-icon {
  font-size: 1.2rem;
  transition: transform 0.3s;
}

.app-button:hover .btn-icon {
  transform: translateX(4px);
}

.travel-notes {
  position: absolute;
  top: 330px;
  left: 0;
  width: 100%;
  display: flex;
  justify-content: space-around;
  pointer-events: none;
  z-index: 0;
}

.travel-notes span {
  color: rgba(15, 118, 110, 0.12);
  font-size: clamp(2.5rem, 8vw, 7rem);
  font-weight: 800;
  letter-spacing: 0.04em;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .subtitle {
    font-size: 1rem;
  }
  
  .apps-container {
    gap: 30px;
    margin: 40px auto;
  }
  
  .app-card {
    width: 100%;
    max-width: 420px;
    padding: 25px;
  }
}

@media (max-width: 480px) {
  .header {
    padding: 50px 15px 40px;
  }
  
  .subtitle {
    font-size: 0.9rem;
    letter-spacing: 2px;
  }
  
  .apps-container {
    margin: 30px auto;
    padding: 0 15px;
  }
  
  .app-card {
    padding: 20px;
  }

  .card-media {
    height: 140px;
    font-size: 3.5rem;
  }
  
  .app-title {
    font-size: 1.4rem;
  }
  
  .app-desc {
    font-size: 0.9rem;
  }
  
  .travel-notes {
    display: none;
  }
}
</style> 
