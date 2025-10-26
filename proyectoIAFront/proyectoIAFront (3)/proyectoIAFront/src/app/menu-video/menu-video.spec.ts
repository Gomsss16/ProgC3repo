import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuVideo } from './menu-video';

describe('MenuVideo', () => {
  let component: MenuVideo;
  let fixture: ComponentFixture<MenuVideo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MenuVideo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuVideo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
