import { registerPlugin } from '@capacitor/core';

import type { SecondaryDisplayPlugin } from './definitions';

const SecondaryDisplay = registerPlugin<SecondaryDisplayPlugin>('SecondaryDisplay', {
  web: () => import('./web').then((m) => new m.SecondaryDisplayWeb()),
});

export * from './definitions';
export { SecondaryDisplay };
