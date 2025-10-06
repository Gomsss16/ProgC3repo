import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuTexto } from './menu-texto';

describe('MenuTexto', () => {
  let component: MenuTexto;
  let fixture: ComponentFixture<MenuTexto>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MenuTexto]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuTexto);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
