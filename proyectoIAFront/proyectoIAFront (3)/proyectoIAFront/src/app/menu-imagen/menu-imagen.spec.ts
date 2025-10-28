import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuImagen } from './menu-imagen';

describe('MenuImagen', () => {
  let component: MenuImagen;
  let fixture: ComponentFixture<MenuImagen>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MenuImagen]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuImagen);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
