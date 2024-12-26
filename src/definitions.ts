export interface SecondaryDisplayPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
