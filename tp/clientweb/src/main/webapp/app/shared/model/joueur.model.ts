export interface IJoueur {
    id?: number;
    nom?: string;
}

export class Joueur implements IJoueur {
    constructor(public id?: number, public nom?: string) {}
}
